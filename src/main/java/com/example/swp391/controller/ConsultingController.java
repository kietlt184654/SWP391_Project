//package com.example.swp391.controller;
//
//import com.example.swp391.entity.AccountEntity;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class ConsultingController {
//    @GetMapping("/HomeConsulting")
//    public String getHomeConsultingPage(Model model, HttpSession session) {
//        AccountEntity currentAccount = (AccountEntity) session.getAttribute("loggedInUser");
//        if (currentAccount != null) {
//            model.addAttribute("loggedInUser", currentAccount);
//            return "HomeConsulting"; // Trả về trang HomeConsulting
//        }
//        return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
//    }
//
//}