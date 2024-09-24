package com.example.swp391.controller;

import com.example.swp391.entity.AccountEnity;
import com.example.swp391.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {
@Autowired
    private AccountService accountService;
@GetMapping("/login")
public String showLoginPage(){
    return "login";
}
@PostMapping("/login")
public String login(@RequestParam("username") String username,
                    @RequestParam("password") String password, Model model){
    AccountEnity loginUser=accountService.userLogin(username, password);
    if(loginUser!=null){
        model.addAttribute("userLogin", loginUser);
        return "homepage";
    }else {
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
}
}
