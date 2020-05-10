package com.stwy.org.tweetystreamer.service;

import com.stwy.org.tweetystreamer.client.TwitterClient;
import com.stwy.org.tweetystreamer.messaging.MessagingService;
import com.stwy.org.tweetystreamer.service.dto.TweetStreamResultDto;
import com.stwy.org.tweetystreamer.service.dto.UserTweetResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TweetService {

    private final TwitterClient twitterClient;

    private final MessagingService messagingService;

    @Value("${client.twitter.encoded_key_secret}")
    private String twitterEncodedAuth;

    public TweetService(TwitterClient twitterClient, MessagingService messagingService) {
        this.twitterClient = twitterClient;
        this.messagingService = messagingService;
    }

    public TweetStreamResultDto processUserTweets(String screenName, Long previousLastTwtProcessed) {
        log.info("Processing {} tweets", screenName);
        String token = generateToken();
        List<UserTweetResponseDto> userTweets = twitterClient.getUserTweets(screenName, previousLastTwtProcessed, token);
        Long processedTweets = userTweets.stream().count();
        Long lastProcessedId = userTweets.stream().mapToLong( tweet-> tweet.getId()).max().orElse(-1L);
        userTweets.stream().forEach(this::sendTweetToProcess);
        log.info("{} {}", processedTweets, lastProcessedId);
        return TweetStreamResultDto.builder()
                .lastTweetProcessed(lastProcessedId)
                .processedTweets(processedTweets)
                .build();
    }

    private UserTweetResponseDto sendTweetToProcess(UserTweetResponseDto userTweetResponseDto) {
        log.info("Sending tweet {} to queue.", userTweetResponseDto.getId());
        log.debug("Tweet content: {}", userTweetResponseDto.getText());
        // TODO send tweet to processing queue
        messagingService.queueTweet(userTweetResponseDto);
        return userTweetResponseDto;
    }

    private String generateToken(){
        log.info("Generating twitter token");
        return "Bearer " + twitterClient.generateBearerToken(twitterEncodedAuth, new TwitterClient.GenerateTokenRequestDto("client_credentials")).getAccess_token();
    }
}
