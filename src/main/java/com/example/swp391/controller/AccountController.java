package com.example.swp391.controller;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.service.AccountService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public String login(@RequestParam("accountName") String accountname, @RequestParam("password") String password, Model model,HttpSession session) {
        AccountEntity account = accountService.login(accountname, password);
        if (account != null) {
            model.addAttribute("message", "Login Successful");
            session.setAttribute("loggedInUser", account);
            return "Homepage";
        }else {
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
    public String register(@Valid @ModelAttribute("userDTO") AccountEntity userDTO, Model model) {
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

        // Tạo tài khoản mới và lưu trực tiếp mật khẩu (không mã hóa)
        AccountEntity account = new AccountEntity();
        account.setAccountName(userDTO.getAccountName());
        account.setPassword(userDTO.getPassword()); // Lưu trực tiếp mật khẩu không mã hóa
        account.setEmail(userDTO.getEmail());

        // Lưu tài khoản vào cơ sở dữ liệu
        accountService.registerUser(account);

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

    @PostMapping("/edit-profile")
    public String editProfile(@RequestParam String name, @RequestParam String phone, @RequestParam String address, HttpSession session, RedirectAttributes redirectAttributes) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("message", "Please login first.");
            return "redirect:/login";
        }

        AccountEntity account = accountService.findByEmail(loggedInUser.getEmail());
        account.setAccountName(name);
        account.setPhoneNumber(phone);
        account.setAddress(address);

        // Cập nhật thông tin người dùng
        accountService.updateAccount(account);

        session.setAttribute("loggedInUser", account); // Cập nhật session
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");

        return "redirect:/profile";
    }





}
