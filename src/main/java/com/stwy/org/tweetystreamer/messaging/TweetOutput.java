package com.stwy.org.tweetystreamer.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TweetOutput {

    @Output("tweety-streamer-output")
    MessageChannel tweetOutputQueue();
}
