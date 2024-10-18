package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Optional<Message> findByMessageTextAndPostedBy(String messageText, int postedBy);
    List<Message> findByPostedBy(int postedBy);
    Optional<Message> findById(int messageId);

}
