package com.stwy.org.tweetystreamer.service.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddNewUserDto {
    @NotEmpty
    private String screenName;
}