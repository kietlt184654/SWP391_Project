package com.example.swp391.controller;//package com.example.swp391.controller;
//
//import com.example.swp391.entity.AccountEntity;
//import com.example.swp391.repository.AccountRepository;
//import com.example.swp391.service.AccountService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//
//import java.util.List;
//
//@Controller
//public class AccountManagement {
//    @Autowired
//    private AccountRepository accountRepository;
//    private AccountService accountService;
//
//    @GetMapping("/showAllAccount")
//    public List<AccountEntity> showAllAccounts(HttpSession httpSession) {
//        return accountRepository.findAll();
//    }
//  // @PostMapping("/createAccount")
//
//
//
//}
