package com.example.swp391.service;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;


public AccountEntity login(String accountName, String password) {
   return accountRepository.findByAccountNameAndPassword(accountName, password);

}
    public boolean checkIfAccountNameExists(String username) {
        return accountRepository.findByAccountName(username) != null;
    }

    public boolean checkIfEmailExists(String email) {
        return accountRepository.findByEmail(email) != null;
    }
    public void registerUser(AccountEntity userDTO) {
        userDTO.setAccountId(accountRepository.findTopByOrderByAccountIdDesc().getAccountId()+1);
        userDTO.setAccountTypeID(2);
        userDTO.setStatus(true);
        accountRepository.save(userDTO);
    }
    public AccountEntity findByEmail(String email) {
    return accountRepository.findByEmail(email);
    }






}
