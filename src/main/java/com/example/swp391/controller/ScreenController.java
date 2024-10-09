package com.example.swp391.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ScreenController {
    @GetMapping("/")
    public String showHomePage() {
        return "Homepage";
    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
}
