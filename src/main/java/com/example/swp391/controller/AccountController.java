package com.example.swp391.controller;

import com.example.swp391.DTO.AccountRegistrationDTO;
import com.example.swp391.entity.AccountEntity;
import com.example.swp391.service.AccountService;
import com.example.swp391.service.EmailService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;



    @PostMapping("/login")
    public String login(@RequestParam("accountName") String accountname, @RequestParam("password") String password, Model model, HttpSession session) {
        AccountEntity account = accountService.login(accountname, password);
        if (account != null) {
            model.addAttribute("message", "Login Successful");
            session.setAttribute("loggedInUser", account);
            return "Homepage";
        } else {
            model.addAttribute("message", "Invalid username or password");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa toàn bộ session
        session.invalidate();
        // Chuyển hướng đến trang đăng nhập hoặc trang chủ
        return "Homepage"; // Thay đổi đường dẫn theo yêu cầu của bạn
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userDTO") AccountRegistrationDTO userDTO, Model model) {
        // Kiểm tra email đã tồn tại
        if (accountService.checkIfEmailExists(userDTO.getEmail())) {
            model.addAttribute("emailError", "Email đã được sử dụng");
            return "register";
        }

        // Kiểm tra tên người dùng đã tồn tại
        if (accountService.checkIfAccountNameExists(userDTO.getAccountName())) {
            model.addAttribute("usernameError", "Tên người dùng đã tồn tại");
            return "register";
        }

        // Đăng ký tài khoản mới bằng AccountRegistrationDTO
        try {
            accountService.registerUser(userDTO);
        } catch (Exception e) {
            model.addAttribute("registrationError", e.getMessage());
            return "register";
        }

        return "login"; // Chuyển hướng tới trang đăng nhập sau khi đăng ký thành công
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Lấy thông tin người dùng từ session
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Nếu người dùng chưa đăng nhập, chuyển hướng về trang đăng nhập
            redirectAttributes.addFlashAttribute("message", "Please login first.");
            return "redirect:/login";
        }

        // Lấy thông tin người dùng mới nhất từ cơ sở dữ liệu
        AccountEntity account = accountService.findByEmail(loggedInUser.getEmail());
        model.addAttribute("accountEntity", account);  // Đẩy thông tin người dùng vào model

        return "profile";  // Trả về view "profile.html"
    }

    @GetMapping("/edit-profile")
    public String editProfileForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("message", "Please login first.");
            return "redirect:/login";
        }

        AccountEntity account = accountService.findByEmail(loggedInUser.getEmail());
        model.addAttribute("accountEntity", account);

        return "editprofile"; // Trả về view "edit-profile.html"
    }

    private static final String uploadDir = "D:\\K5\\SWP391\\UpdateImg";

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam("profileImage") MultipartFile profileImage,
                                @RequestParam("accountName") String accountName,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("address") String address,
                                HttpSession session, RedirectAttributes redirectAttributes) {

        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("message", "Please log in first.");
            return "login";
        }

        try {
            // Nếu người dùng có upload ảnh mới
            if (!profileImage.isEmpty()) {
                // Lưu file ảnh vào thư mục upload
                String fileName = profileImage.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir, fileName);

                // Tạo thư mục nếu chưa tồn tại
                if (!Files.exists(uploadPath.getParent())) {
                    Files.createDirectories(uploadPath.getParent());
                }

                // Lưu file ảnh vào thư mục
                profileImage.transferTo(new File(uploadPath.toString()));

                // Lưu đường dẫn ảnh vào cơ sở dữ liệu (chỉ đường dẫn tương đối)
                loggedInUser.setImages("/uploads/" + fileName);
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Failed to upload image.");
            e.printStackTrace();
        }

        // Cập nhật các thông tin khác của người dùng
        loggedInUser.setAccountName(accountName);
        loggedInUser.setEmail(email);
        loggedInUser.setPassword(password);  // Nên mã hóa mật khẩu trước khi lưu
        loggedInUser.setPhoneNumber(phoneNumber);
        loggedInUser.setAddress(address);

        // Lưu thông tin đã cập nhật vào cơ sở dữ liệu
//        accountService.updateAccount(loggedInUser);

        // Cập nhật session với thông tin mới của người dùng
        session.setAttribute("loggedInUser", loggedInUser);
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");

        return "profile";  // Quay lại trang profile sau khi lưu thành công
    }
    // Hiển thị trang forgot-password.html
    @GetMapping("/forgot-password-form")
    public String showForgotPasswordForm() {
        return "forgotPassword";  // Trả về view forgot-password.html
    }
    // Gửi email lấy token khi quên mật khẩu
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        try {
            accountService.sendResetPasswordLink(email);  // Gọi service để gửi email với token
            redirectAttributes.addFlashAttribute("message", "Password reset link sent to your email.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Email not found.");
        }
        return "redirect:/account/forgot-password-form";  // Điều hướng đến trang quên mật khẩu
    }
    @GetMapping("/reset-password-form")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);  // Đưa token vào model để sử dụng trong form
        return "resetPassword";  // Trả về trang resetPassword.html
    }

    // Đặt lại mật khẩu bằng token
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("newPassword") String newPassword,
                                RedirectAttributes redirectAttributes) {
        try {
            accountService.updatePassword(token, newPassword);  // Cập nhật mật khẩu mới
            redirectAttributes.addFlashAttribute("message", "Password updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired token.");
        }
        return "redirect:/login";  // Điều hướng đến trang đăng nhập sau khi đặt lại mật khẩu thành công
    }
}
