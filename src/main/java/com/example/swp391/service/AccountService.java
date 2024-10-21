package com.example.swp391.service;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;


public AccountEntity login(String accountName, String password) {
   return accountRepository.findByAccountNameAndPassword(accountName, password);

}
    public boolean checkIfEmailExists(String email) {
        return accountRepository.findByEmail(email)!=null;
    }

    public boolean checkIfAccountNameExists(String username) {
        return accountRepository.findByAccountName(username)!=null;
    }
    public void registerUser(AccountEntity userDTO) {
        userDTO.setAccountId(accountRepository.findTopByOrderByAccountIdDesc().getAccountId()+1);
        userDTO.setAccountTypeID("Customer");
        userDTO.setStatus(true);
        accountRepository.save(userDTO);
    }
    public AccountEntity findByEmail(String email) {
    return accountRepository.findByEmail(email);
    }
//    public void updateResetToken(String token, String email) {
//        AccountEntity user = accountRepository.findByEmail(email);
//        if (user != null) {
//            user.setResetToken(token);
//            accountRepository.save(user);
//        }
//
//
//    }
    public void updatePassword(AccountEntity user, String newPassword) {
        // Không mã hóa, chỉ cập nhật mật khẩu trực tiếp
        user.setPassword(newPassword);

        // Lưu đối tượng user đã cập nhật vào cơ sở dữ liệu
        accountRepository.save(user);
    }

    public void save(AccountEntity user) {
    accountRepository.save(user);
    }


    public AccountEntity findByToken(String token) {
        return accountRepository.findByResetToken(token); // Tìm theo token
    }


    public void updateAccount(AccountEntity account) {
        accountRepository.save(account);
    }
    public List<AccountEntity> getAllCustomers() {
        return accountRepository.findAll();
    }

}
