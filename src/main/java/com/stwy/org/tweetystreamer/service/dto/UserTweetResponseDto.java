package com.stwy.org.tweetystreamer.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserTweetResponseDto {
    @JsonProperty("created_at")
    private String createdAt;

    private Long id;

    private String text;

    @JsonProperty("retweet_count")
    private Long retweetCount;

    @JsonProperty("favorite_count")
    private Long favoriteCount;

    private Boolean favorited;

    private Boolean retweeted;

}
