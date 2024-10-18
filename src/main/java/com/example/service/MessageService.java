package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.controller.MessageDto;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    MessageRepository repo;
    @Autowired
    AccountRepository repoAccount;
    public Optional<Message> save(MessageDto msgDto) {
        String text = msgDto.getMessageText();
        // if message is too long, too short, or if postedBy doesn't exist, return null
        if (text.length() == 0 | text.length() > 255)
            return Optional.empty();
        if (repoAccount.getById(msgDto.getPostedBy()) == null)
            return Optional.empty();
        //
        repo.save(new Message(
            msgDto.getPostedBy(),
            msgDto.getMessageText(),
            msgDto.getTimePostedEpoch()
            ));

        return repo.getByMessageTextAndPostedBy(msgDto.getMessageText(), msgDto.getPostedBy());
    }

    public List<Message> findAll() {
        return repo.findAll();
    }

    public Optional<Message> findById(int id) {
        return findById(id);
    }

    public Optional<Integer> deleteById(int id) {
        Optional<Integer> numRowsDeleted = Optional.of(0);
        if (repo.findById(id) != null)
            numRowsDeleted = Optional.of(1);
        //
        repo.deleteById(Integer.valueOf(id));

        return numRowsDeleted;
    }

}
