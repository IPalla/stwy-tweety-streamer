package com.stwy.org.tweetystreamer.client;

import com.stwy.org.tweetystreamer.service.dto.UserTweetResponseDto;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "twitterClient", url = "${client.twitter.baseurl}", configuration = TwitterClient.Configuration.class)
public interface TwitterClient {

    @PostMapping(value = "/oauth2/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GenerateTokenResponseDto generateBearerToken(@RequestHeader("Authorization") String authorization, @RequestBody GenerateTokenRequestDto generateTokenRequestDto);

    @GetMapping(value="1.1/statuses/user_timeline.json?screen_name=elonmusk&trim_user=1")
    List<UserTweetResponseDto> getUserTweets(@RequestParam("screen_name")String screenName, @RequestParam("since_id") Long sinceId, @RequestHeader("Authorization") String authorization);

    @Data
    @AllArgsConstructor
    class GenerateTokenRequestDto {
        private String grant_type = "client_credentials";
    }

    @Data
    class GenerateTokenResponseDto {
        private String access_token;
    }

    class Configuration {
        @Bean
        Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
            return new SpringFormEncoder(new SpringEncoder(converters));
        }
    }

}
