//package com.example.swp391.controller;
//
//import com.example.swp391.entity.AccountEnity;
//import com.example.swp391.repository.AccountRepository;
//import com.example.swp391.service.AccountService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
//@Controller
//public class AccountManagement {
//    @Autowired
//    private AccountRepository accountRepository;
//    private AccountService accountService;
////    @DeleteMapping("/{id}")
////    public ResponseEntity<String> deleteAccount(@PathVariable int id, HttpSession httpSession) {
////        try{
////            accountService.deleteAccount(id);
////            return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
////
////        } catch (Exception e) {
////            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
////        }
////
////    }
////    @GetMapping("/showAllAccount")
////    public List<AccountEnity> showAllAccounts(HttpSession httpSession) {
////        return accountRepository.findAll();
////    }
////    @GetMapping("/searchByType")
////    public List<AccountEnity> searchByType(HttpSession httpSession) {
////
////    }
//
//
//}
