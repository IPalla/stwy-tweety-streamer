package com.stwy.org.tweetystreamer.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TweetStreamResultDto {
    private Long processedTweets, lastTweetProcessed;
}
