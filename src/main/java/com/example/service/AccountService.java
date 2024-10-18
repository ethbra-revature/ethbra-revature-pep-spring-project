package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository repo;

    public Optional<Account> registerAccount(String username, String password) {
        if (password.length() < 4 | username.length() == 0)
            return Optional.empty();
        // find if username exists, return empty
        
        if (repo.findByUsername(username).isPresent())
            return Optional.empty();
        //  return new account

        repo.save(new Account(username, password));
        return repo.findByUsername(username);
    }

    public Optional<Account> login(String username, String password) {
        return repo.findByUsernameAndPassword(username, password);
    }
}
