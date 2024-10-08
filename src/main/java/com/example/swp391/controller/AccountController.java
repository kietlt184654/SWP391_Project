package com.example.swp391.controller;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public String login(@RequestParam("accountName") String accountname, @RequestParam("password") String password, Model model) {
        AccountEntity account = accountService.login(accountname, password);
        if (account != null) {
            model.addAttribute("message", "Login Successful");
            return "Homepage";
        }else {
            model.addAttribute("message", "Invalid username or password");
            return "login";
        }





    }





}
