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

    @PostMapping("/register")
public String register(@Valid @ModelAttribute("userDTO") AccountEntity userDTO, Model model) {
        if (accountService.checkIfEmailExists(userDTO.getEmail())&&userDTO.getEmail().matches("")) {
            model.addAttribute("emailError", "Email đã được sử dụng");
            return "register";
        }
        // Kiểm tra username đã tồn tại
        if (accountService.checkIfAccountNameExists(userDTO.getAccountName())) {
            model.addAttribute("usernameError", "Tên người dùng đã tồn tại");
            return "register";
        }
        AccountEntity account = new AccountEntity();
        account.setAccountName(userDTO.getAccountName());
        account.setPassword(userDTO.getPassword());
        account.setEmail(userDTO.getEmail());
        accountService.registerUser(account);
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa thông tin người dùng khỏi session
        session.invalidate();
        // Chuyển hướng tới trang Homepage
        return "redirect:/homepage";
    }
    @GetMapping
    public String showProfile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Lấy thông tin người dùng từ session
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Nếu người dùng chưa đăng nhập, chuyển hướng về trang login
            redirectAttributes.addFlashAttribute("message", "Please login first.");
            return "redirect:/login";
        }

        // Lấy thông tin cập nhật từ database để đảm bảo dữ liệu mới nhất
        AccountEntity account = accountService.findByEmail(loggedInUser.getEmail());
        model.addAttribute("accountEntity", account);
        return "profile";
    }





}
