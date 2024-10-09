package com.example.swp391.controller;

import com.example.swp391.entity.AccountEnity;
import com.example.swp391.repository.AccountRepository;
import com.example.swp391.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public String login(@RequestParam("accountName") String accountName,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {

        AccountEnity loginUser = accountService.userLogin(accountName, password);

        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser);
            model.addAttribute("userLogin", loginUser);

            int role = loginUser.getAccountTypeID();
            switch (role) {
                case 1:
                case 2:
                case 3:
                    return "Homepage";  // Điều chỉnh tên view nếu cần
                default:
                    model.addAttribute("errorAccount", "Quyền của bạn không hợp lệ");
                    return "login";
            }
        } else {
            model.addAttribute("errorAccount", "Invalid username or password");
            return "login";
        }
    }


//    @PostMapping("/register")
//    public String register(AccountEnity accountEnity, Model model) {
//        if (accountService.checkExistingAccount(accountEnity.getAccountName())) {
//            model.addAttribute("errorAccount", "Account already exists");
//            return "register";
//        }
//        if (!accountEnity.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
//            model.addAttribute("errorAccount", "Invalid email address");
//            return "register";
//        }
//        if (accountService.checkExistingEmail(accountEnity.getEmail())) {
//            model.addAttribute("errorAccount", "Email already exists");
//            return "register";
//        } else
//            accountService.registerAccount(accountEnity);
//        return "redirect:/loginForm";
//    }
//
//    @GetMapping("/profile")
//    public String viewProfile(HttpSession session, Model model) {
//        AccountEnity loginUser = (AccountEnity) session.getAttribute("loginUser");
//        int accountId = loginUser.getAccountId();
//        AccountEnity account = accountService.findByAccountId(accountId);
//        model.addAttribute("account", account);
//        return "viewProfile";
//    }
//
//
//    @PostMapping("/updateProfile")
//    public String updateProfile(@ModelAttribute("account") AccountEnity accountEnity, @RequestParam("currentPassword") String currentPassword,
//                                @RequestParam(value = "newPassword", required = false) String newPassword,
//                                @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
//                                @RequestParam(value = "accountName", required = false) String accountName,
//                                Model model, HttpSession session) throws Exception {
//        AccountEnity loginUser = (AccountEnity) session.getAttribute("loginUser");
//        int accountId = loginUser.getAccountId();
//        AccountEnity account = accountService.findByAccountId(accountId);//check right account
//        try {
//            accountService.updateProfile(account, currentPassword, newPassword, confirmPassword, accountName);
//        } catch (Exception e) {
//            model.addAttribute("errorAccount", e.getMessage());
//        }
//        AccountEnity updatedAccount = accountService.findByAccountId(accountId);
//        model.addAttribute("account", updatedAccount);
//        return "redirect:/profile";
//    }



}
