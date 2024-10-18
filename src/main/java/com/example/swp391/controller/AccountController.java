package com.example.swp391.controller;

import com.example.swp391.DTO.AccountRegistrationDTO;
import com.example.swp391.entity.AccountEntity;
import com.example.swp391.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Hiển thị form đăng nhập (Spring Security sẽ tự xử lý việc xác thực)
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // Trả về trang đăng nhập
    }

    // Hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new AccountRegistrationDTO());
        return "register";  // Trả về trang đăng ký
    }

    // Xử lý đăng ký người dùng mới
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userDTO") AccountRegistrationDTO userDTO,
                           Model model, RedirectAttributes redirectAttributes) {
        try {
            // Gọi service để xử lý đăng ký người dùng mới
            accountService.registerNewAccount(userDTO);

            // Nếu thành công, chuyển hướng đến trang đăng nhập với thông báo
            redirectAttributes.addFlashAttribute("message", "Đăng ký thành công, vui lòng đăng nhập.");
            return "redirect:/account/login";

        } catch (Exception e) {
            // Nếu có lỗi, hiển thị thông báo lỗi phù hợp
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // Hiển thị trang hồ sơ tài khoản của người dùng đã đăng nhập
    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String accountName = userDetails.getUsername();
        AccountEntity account = accountService.findByAccountName(accountName);  // Lấy thông tin tài khoản
        if (account != null) {
            model.addAttribute("accountEntity", account);
            return "profile";  // Trả về trang thông tin cá nhân
        } else {
            model.addAttribute("message", "Không tìm thấy tài khoản");
            return "error";  // Trả về trang lỗi nếu không tìm thấy tài khoản
        }
    }

    // Hiển thị form quên mật khẩu
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";  // Trả về trang nhập email quên mật khẩu
    }

    // Xử lý việc gửi email khôi phục mật khẩu
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        try {
            accountService.sendResetPasswordLink(email);  // Gửi link khôi phục mật khẩu qua email
            model.addAttribute("message", "Link khôi phục mật khẩu đã được gửi đến email của bạn.");
        } catch (Exception e) {
            model.addAttribute("error", "Email không tồn tại trong hệ thống.");
        }
        return "forgot_password_form";  // Quay lại trang nhập email quên mật khẩu
    }

    // Hiển thị form đặt lại mật khẩu sau khi nhấp vào link trong email
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);  // Gửi token vào form
        return "reset_password_form";  // Trả về trang đặt lại mật khẩu
    }

    // Xử lý đặt lại mật khẩu
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       Model model, RedirectAttributes redirectAttributes) {
        try {
            accountService.updatePassword(token, password);  // Cập nhật mật khẩu mới
            redirectAttributes.addFlashAttribute("message", "Mật khẩu đã được cập nhật thành công.");
            return "redirect:/account/login";  // Chuyển hướng tới trang đăng nhập sau khi đặt lại mật khẩu thành công
        } catch (Exception e) {
            model.addAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
            return "reset_password_form";  // Quay lại trang đặt lại mật khẩu nếu có lỗi
        }
    }
}
