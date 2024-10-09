package com.example.swp391.service;

import com.example.swp391.entity.AccountEnity;
import com.example.swp391.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

//    public AccountEnity userLogin(String accountName, String password) {
//        AccountEnity account = accountRepository.findByAccountNameAndPassword(accountName, password);
//        if (account != null && account.getPassword().equals(password)) {
//            return account;
//        }
//        return null;
//    }
public AccountEnity userLogin(String accountName, String password) {
    AccountEnity account = accountRepository.findByAccountName(accountName);
    if (account != null && account.getPassword().equals(password)) {
        return account;
    }
    return null;
}


//    public AccountEnity findAccountByTypeID(Integer accountTypeID) {
//        return accountRepository.findByAccountTypeID(accountTypeID);
//    }


//    public AccountEnity registerAccount(AccountEnity account) {
//        return accountRepository.save(account);
//    }
//
//    public boolean checkExistingAccount(String accountName) {
//        return accountRepository.findByAccountName(accountName) != null;
//    }
//
//    public boolean checkExistingEmail(String accountEmail) {
//        return accountRepository.findByEmail(accountEmail) != null;
//    }
//
//    public AccountEnity findByAccountId(int AccountId) {
//        return accountRepository.findByAccountId(AccountId)
//                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
//    }
//
//
//    public void updateProfile(AccountEnity updateAccount, String currentPassword, String newPassword, String confirmPassword,String accountName) throws Exception {
//        AccountEnity existingAccount = findByAccountId(updateAccount.getAccountId());
//        if (existingAccount.getPassword().equals(currentPassword)) {
//            throw new Exception("Current password doesn't match");
//        }
//        if (!newPassword.isEmpty() && newPassword != null) {
//            if (!newPassword.equals(confirmPassword)) {
//
//                throw new Exception("New password don't match with confirm password");
//            }
//            existingAccount.setPassword(newPassword);
//        }
//if (existingAccount.getPassword().equals(newPassword)&&existingAccount.getAccountName().equals(accountName)) {
//    throw  new Exception("Please Change Password or account name");
//}
//        existingAccount.setAccountName(updateAccount.getAccountName());
//        existingAccount.setEmail(updateAccount.getEmail());
//        existingAccount.setPhoneNumber(updateAccount.getPhoneNumber());
//        existingAccount.setAddress(updateAccount.getAddress());
//        accountRepository.save(existingAccount);
//
//    }
//public void deleteAccount(int AccountId) throws Exception {
//        if(accountRepository.existsById(AccountId)) {
//            accountRepository.deleteById(AccountId);
//        }else {
//            throw new Exception("Account Not Found");
//        }
//}
//public List<AccountEnity> showAllAccounts() {
//       return accountRepository.findAll();
//}

}
