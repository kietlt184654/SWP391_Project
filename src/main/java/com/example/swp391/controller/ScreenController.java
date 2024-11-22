package com.example.swp391.controller;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.ProjectEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ScreenController {
    @GetMapping("/")
    public String showHomePage(HttpSession session) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        // Kiểm tra nếu chưa đăng nhập (Guest)
        if (loggedInUser == null) {
            return "Homepage"; // Người dùng chưa đăng nhập vẫn xem được trang chủ
        }

        // Kiểm tra quyền và chuyển hướng tới trang phù hợp
        switch (loggedInUser.getAccountTypeID()) {
            case "Manager":
                return "redirect:/manager";
            case "Consulting Staff":
                return "redirect:/consultingHome";
            case "Construction Staff":
                return "redirect:/dashboard";
            case "Design Staff":
                return "redirect:/designStaff/designs/inprogress";
            case "Customer":
                return "Homepage"; // Khách hàng đã đăng nhập vẫn xem được trang chủ
            default:
                // Nếu quyền không hợp lệ, đưa về trang đăng nhập hoặc lỗi
                return "redirect:/login";
        }
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
    @GetMapping("/forgot-password-form")
    public String showForgotPasswordForm(Model model) {
        return "forgotPassword";
    }



}