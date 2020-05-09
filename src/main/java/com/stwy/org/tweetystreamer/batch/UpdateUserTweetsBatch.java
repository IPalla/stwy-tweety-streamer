package com.stwy.org.tweetystreamer.batch;

import com.stwy.org.tweetystreamer.service.ProcessedUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UpdateUserTweetsBatch {

    private final ProcessedUserService processedUserService;

    private final int updateMinPeriod = 1 ; // minutes between update.

    @Async
    @Scheduled(fixedRate = updateMinPeriod * 10000)
    public void updateUsersTweets(){
        log.info("Running update user tweets task");
        processedUserService.processAllUsers().stream()
            .forEach(processedUser -> log.info("User {} processed successfully", processedUser.getScreenName()));
        log.info("Update user tweets task finished.");
    }
}
