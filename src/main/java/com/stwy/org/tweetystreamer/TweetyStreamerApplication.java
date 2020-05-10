package com.stwy.org.tweetystreamer;

import com.stwy.org.tweetystreamer.messaging.TweetOutput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaAuditing
@EnableJpaRepositories
@EnableTransactionManagement
@EnableFeignClients
@EnableScheduling
@Configuration
@EnableAsync
@EnableBinding(TweetOutput.class)
public class TweetyStreamerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetyStreamerApplication.class, args);
	}

}
