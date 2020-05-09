package com.stwy.org.tweetystreamer.service;

import com.stwy.org.tweetystreamer.model.ProcessedUser;
import com.stwy.org.tweetystreamer.model.repository.ProcessedUserRepository;
import com.stwy.org.tweetystreamer.service.dto.TweetStreamResultDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProcessedUserService {
    private final ProcessedUserRepository processedUserRepository;
    private final TweetService tweetService;

    public List<ProcessedUser> getAllProcessedUsers() {
        return processedUserRepository.findAll();
    }

    public ProcessedUser insertNewProcessedUser(String screenName){
        log.info("Inserting new user to process {}", screenName);
        ProcessedUser newProcessedUser = ProcessedUser.builder()
                .screenName(screenName)
                .processedTweets(0L)
                .build();
        return processedUserRepository.save(newProcessedUser);
    }

    public List<ProcessedUser> processAllUsers() {
        log.info("Processing all users");
        return processedUserRepository.findAll().stream()
                .map(this::processUser)
                .map(processedUserRepository::save)
                .collect(Collectors.toList());
    }

    private ProcessedUser processUser(ProcessedUser processedUser) {
        log.info("Processing user {}", processedUser.getScreenName());
        try {
            TweetStreamResultDto tweetStreamResultDto = tweetService.processUserTweets(processedUser.getScreenName(), processedUser.getLastProcessedTweet());
            if ( tweetStreamResultDto.getProcessedTweets() > 0 ){
                log.info("User {} has no recent tweets to update", processedUser.getScreenName());
                // Only update this attributes if more than 0 tweets has been processed
                processedUser.setProcessedTweets(processedUser.getProcessedTweets() + tweetStreamResultDto.getProcessedTweets());
                processedUser.setLastProcessedTweet(tweetStreamResultDto.getLastTweetProcessed());
            }
        } catch (Exception e) {
            log.error("Error while processing user {} tweets: {}", processedUser.getScreenName(), e.getMessage());
        } finally {
            // Even though no tweets has been processed modified date should be modified
            processedUser.setModifiedDate(Instant.now());
            return processedUser;
        }
    }
}
