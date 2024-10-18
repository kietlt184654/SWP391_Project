package com.example.swp391.controller;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Hiển thị form đăng nhập (Spring Security sẽ tự xử lý việc xác thực)
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // Trả về trang đăng nhập
    }

    // Xử lý đăng ký người dùng mới
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userDTO") AccountEntity userDTO, Model model, RedirectAttributes redirectAttributes) {
        // Kiểm tra xem email đã tồn tại chưa
        if (accountService.checkIfEmailExists(userDTO.getEmail())) {
            model.addAttribute("emailError", "Email đã được sử dụng");
            return "register";  // Quay lại trang đăng ký nếu có lỗi
        }

        // Kiểm tra xem tên tài khoản đã tồn tại chưa
        if (accountService.checkIfAccountNameExists(userDTO.getAccountName())) {
            model.addAttribute("usernameError", "Tên người dùng đã tồn tại");
            return "register";  // Quay lại trang đăng ký nếu có lỗi
        }

        // Tạo tài khoản mới và mã hóa mật khẩu
        AccountEntity account = new AccountEntity();
        account.setAccountName(userDTO.getAccountName());
        account.setPassword(passwordEncoder.encode(userDTO.getPassword()));  // Mã hóa mật khẩu
        account.setEmail(userDTO.getEmail());
        account.setPhoneNumber(userDTO.getPhoneNumber());
        account.setStatus(true);  // Mặc định kích hoạt tài khoản

        // Lưu tài khoản vào cơ sở dữ liệu
        accountService.registerUser(account);

        // Thông báo thành công và chuyển hướng tới trang đăng nhập
        redirectAttributes.addFlashAttribute("message", "Đăng ký thành công, vui lòng đăng nhập.");
        return "redirect:/account/login";
    }

    // Hiển thị trang hồ sơ tài khoản của người dùng đã đăng nhập
    @GetMapping("/profile")
    public String showProfile(Model model) {
        // Lấy thông tin người dùng hiện tại từ Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accountName = authentication.getName();

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
