package com.example.swp391.controller;
import com.example.swp391.entity.AccountEntity;
import com.example.swp391.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetPasswordController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String newPassword, Model model) {
        // Kiểm tra token hợp lệ (trong ví dụ này token tạm thời là "abc123")
        if (!token.equals("abc123")) {
            model.addAttribute("error", "Token không hợp lệ.");
            return "reset-password";
        }

        // Cập nhật mật khẩu mới trong cơ sở dữ liệu
        // (Trong ví dụ này, bạn cần tìm kiếm user theo token và cập nhật mật khẩu)
        AccountEntity user = accountService.findByToken(token); // Giả sử bạn có phương thức này
        if (user != null) {
            user.setPassword(newPassword);
            accountService.save(user);
            model.addAttribute("message", "Mật khẩu của bạn đã được cập nhật thành công.");
        } else {
            model.addAttribute("error", "Token không hợp lệ.");
        }

        return "reset-password";
    }
}
