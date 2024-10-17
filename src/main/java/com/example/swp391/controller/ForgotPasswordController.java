package com.example.swp391.controller;
import com.example.swp391.service.AccountService;
import com.example.swp391.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotPasswordController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        // Kiểm tra xem email có tồn tại trong hệ thống không
        if (!accountService.checkIfEmailExists(email)) {
            model.addAttribute("error", "Email không tồn tại trong hệ thống.");
            return "forgotPassword"; // Quay lại form và thông báo lỗi
        }

        // Nếu email tồn tại, gửi email với link reset mật khẩu
        String token = "abc123"; // Token có thể là một chuỗi ngẫu nhiên, lưu trong cơ sở dữ liệu
        String resetPasswordLink = "http://localhost:8080/reset-password?token=" + token;
        String subject = "Reset Password";
        String text = "Click this link to reset your password: " + resetPasswordLink;

        emailService.sendSimpleEmail(email, subject, text);
        model.addAttribute("message", "Email đã được gửi, vui lòng kiểm tra hộp thư của bạn.");

        return "forgotPassword"; // Trả về trang thông báo đã gửi email
    }
}
