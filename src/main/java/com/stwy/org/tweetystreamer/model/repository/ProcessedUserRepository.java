package com.stwy.org.tweetystreamer.model.repository;

import com.stwy.org.tweetystreamer.model.ProcessedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedUserRepository extends JpaRepository<ProcessedUser, String> {
}
