package com.example.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    AccountRepository repo;

    public Optional<Account> registerAccount(String username, String password) {
        if (password.length() < 4 | username.length() == 0) {

            logger.info("credentials don't meet requirements");

            return Optional.empty();
        }

        // find if username exists, return empty
        if (repo.findByUsername(username).isPresent()) {
            logger.info("Username already exists!");
            return Optional.empty();
        }
        
        logger.info("New account registered");
        repo.save(new Account(username, password));

        return repo.findByUsername(username);
    }

    public Optional<Account> login(String username, String password) {

        return repo.findByUsernameAndPassword(username, password);
        
    }
}
