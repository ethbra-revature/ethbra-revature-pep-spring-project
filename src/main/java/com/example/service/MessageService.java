package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.controller.MessageDto;
import com.example.entity.Account;
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

        Optional<Account> sender = repoAccount.findById(msgDto.getPostedBy());
        if (sender.isEmpty())
            return Optional.empty();
        //
        repo.saveAndFlush(new Message(
                msgDto.getPostedBy(),
                msgDto.getMessageText(),
                msgDto.getTimePostedEpoch()));

        return repo.findByMessageTextAndPostedBy(msgDto.getMessageText(), msgDto.getPostedBy());
    }

    public List<Message> findAll() {
        return repo.findAll();
    }

    public Optional<Message> findById(int id) {
        return repo.findById(id);
    }

    public int deleteById(int id) {
        if (repo.findById(id).isEmpty()) {
            return 0;
        }
        repo.deleteById(id);
        return 1;
    }

    public int patchMessage(int messageId, MessageDto msgDto) {
        if (msgDto.getMessageText().length() == 0 | msgDto.getMessageText().length() > 255)
            return 0;

        Optional<Message> msgId = repo.findById(messageId);

        if (msgId.isPresent()) {
            Message msg = msgId.get();
            msg.setMessageText(msgDto.getMessageText());

            repo.save(msg);
            return 1;
        }

        // no message with that ID exists, return 0
        return 0;
    }

    public Optional<Message> updateMessage(int id, MessageDto msgDto) {
        if (msgDto.getMessageText().length() == 0 | msgDto.getMessageText().length() > 255)
            return Optional.empty();

        Optional<Message> idMessage = repo.findById(id);

        if (idMessage.isPresent()) {
            Message msg = new Message(id, msgDto.getPostedBy(),
                    msgDto.getMessageText(),
                    msgDto.getTimePostedEpoch());
            idMessage = Optional.of(repo.save(msg));

            return idMessage;
        }
        //
        return Optional.empty();

    }

    public List<Message> findByAccountId(int postedBy) {
        return repo.findByPostedBy(postedBy);
    }
}
