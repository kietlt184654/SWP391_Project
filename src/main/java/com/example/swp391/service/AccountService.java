package com.example.swp391.service;

import com.example.swp391.entity.AccountEnity;
import com.example.swp391.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public AccountEnity userLogin(String username, String password) {
        AccountEnity user=accountRepository.findByUserName(username);
if (user.getPassword().equals(password)&&user!=null) {
    return user;
}return null;
    }
}
