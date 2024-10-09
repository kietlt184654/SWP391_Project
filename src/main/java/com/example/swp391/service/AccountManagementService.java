//package com.example.swp391.service;
//
//import com.example.swp391.entity.AccountEnity;
//import com.example.swp391.repository.AccountRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AccountManagementService {
//    @Autowired
//    private AccountRepository accountRepository;
//    public void deleteAccount(int AccountId) throws Exception {
//        if(accountRepository.existsById(AccountId)) {
//            accountRepository.deleteById(AccountId);
//        }else {
//            throw new Exception("Account Not Found");
//        }
//    }
//    public List<AccountEnity> showStaff() {
//        return accountRepository.findAll();
//    }
////    public List<AccountEnity> searchByType(String type) {
////        return
////    }
//
//}
