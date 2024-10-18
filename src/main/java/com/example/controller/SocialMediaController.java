package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */

@RestController
@RequestMapping("/")
public class SocialMediaController {
    public final Logger logger = LoggerFactory.getLogger(SocialMediaController.class);
    
    @Autowired
    AccountService accService;
    @Autowired
    MessageService msgService;


    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody AccountDto accDto) {
        logger.info("Calling POST request for Account registration");

        Optional<Account> acc = accService.registerAccount(accDto.getUsername(), accDto.getPassword());
        if (acc.isPresent())
            return ResponseEntity.status(200).body(acc.get());
        //

        return ResponseEntity.status(409).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody AccountDto accDto) {
        logger.info("Calling POST request for Account login");

        Optional<Account> acc = accService.login(accDto.getUsername(), accDto.getPassword());

        if (acc.isPresent()) {
            return ResponseEntity.status(200).body(acc.get());
        }

        return ResponseEntity.status(401).body(null);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody MessageDto msgDto) {
        logger.info("Calling POST request for Message creation");
        
        Optional<Message> msg = msgService.save(msgDto);
        if (msg.isPresent())
            return ResponseEntity.status(200).body(msg.get());
        //
        return ResponseEntity.status(401).body(null);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        logger.info("Calling GET request for all Messages");
        
        List<Message> messages = msgService.findAll();

        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{messageId")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        logger.info("Calling GET request for Message by ID");

        Optional<Message> msg = msgService.findById(messageId);
        return ResponseEntity.status(200).body(msg.get());
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int id) {
        logger.info("Calling DELETE request for Message by ID");

        Optional<Integer> numRowsDeleted = msgService.deleteById(id);
        return ResponseEntity.status(200).body(numRowsDeleted.get());
    }

    // @PatchMapping("/messages/{messageId}")
    // public ResponseEntity<Integer> updateMessage(@RequestBody MessageDto msgDto) {
        
    // }
    
    // @ExceptionHandler(EntityNotFoundException)
}
