package com.stwy.org.tweetystreamer.messaging;

import com.stwy.org.tweetystreamer.service.dto.UserTweetResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessagingService {

    private final TweetOutput tweetOutput;

    public MessagingService(TweetOutput tweetOutput) {
        this.tweetOutput = tweetOutput;
    }

    public void queueTweet(UserTweetResponseDto tweet){
        tweetOutput.tweetOutputQueue().send(message(tweet));
    }

    private static final <T> Message<T> message(T body) {
        return MessageBuilder.withPayload(body)
                .build();
    }

}
