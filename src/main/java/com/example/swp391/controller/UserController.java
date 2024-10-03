//package com.example.swp391.controller;
//
//import com.example.swp391.entity.UserEntity;
//import com.example.swp391.service.UserService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class UserController {
//@Autowired
//    private UserService userService;
//
//@GetMapping("/login")
//public String showLoginPage(){
//    return "login";
//}
//
//@PostMapping("/login")
//public String login(@RequestParam("username") String username,
//                    @RequestParam("password") String password, Model model, HttpSession session){
//    UserEntity loginUser=userService.userLogin(username, password);
//    if(loginUser!=null){
//
//        session.setAttribute("userLogin", loginUser);
//        return "homePage";
//    }else {
//        model.addAttribute("error", "Invalid username or password");
//        return "login";
//    }
//}
//}
