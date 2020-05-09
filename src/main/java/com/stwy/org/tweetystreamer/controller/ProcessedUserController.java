package com.stwy.org.tweetystreamer.controller;

import com.stwy.org.tweetystreamer.service.dto.AddNewUserDto;
import com.stwy.org.tweetystreamer.model.ProcessedUser;
import com.stwy.org.tweetystreamer.service.ProcessedUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/processed-users")
@Slf4j
@AllArgsConstructor
public class ProcessedUserController {

    private final ProcessedUserService processedUserService;

    @GetMapping()
    ResponseEntity<List<ProcessedUser>> getAllProcessedUsers(){
        return ResponseEntity.ok(processedUserService.getAllProcessedUsers());
    }

    @PostMapping()
    ResponseEntity<ProcessedUser> addNewUserToProcess(@Valid @RequestBody AddNewUserDto addNewUserDto){
        return ResponseEntity.ok(processedUserService.insertNewProcessedUser(addNewUserDto.getScreenName()));
    }


}
