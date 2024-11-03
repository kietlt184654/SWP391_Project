package com.example.swp391.controller;

import com.example.swp391.entity.ProjectEntity;
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
    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        return "profile";
    }
    @GetMapping("/forgetPassword")
    public String showForgetPasswordPage(Model model) {
        return "forgotPassword";
    }
    @GetMapping("/design")
    public String showDesignPage(Model model) {
        return "Design";
    }
    @GetMapping("/designDetail")
    public String showDesignDetailPage(Model model) {
        return "viewProductDetail";
    }
    @GetMapping("/designService")
    public String showDesignServicePage(Model model) {
        return "designService";
    }
    @GetMapping("/maintenanceServices")
    public String showMaintenanceServicesPage(Model model) {
        return "MaintenanceServices";
    }



}