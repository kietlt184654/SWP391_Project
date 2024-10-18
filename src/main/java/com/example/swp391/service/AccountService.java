package com.example.swp391.service;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;  // Giả định có một lớp gửi email

    // Đăng nhập
    public AccountEntity login(String accountName, String password) {
        AccountEntity account = accountRepository.findByAccountName(accountName);
        if (account != null && passwordEncoder.matches(password, account.getPassword())) {
            return account;  // Trả về tài khoản nếu mật khẩu đúng
        }
        return null;  // Đăng nhập thất bại
    }

    // Đăng ký người dùng mới
    @Transactional
    public void registerUser(AccountEntity account) {
        accountRepository.save(account);
    }

    // Kiểm tra xem email đã tồn tại chưa
    public boolean checkIfEmailExists(String email) {
        return accountRepository.findByEmail(email) != null;
    }

    // Kiểm tra xem tên tài khoản đã tồn tại chưa
    public boolean checkIfAccountNameExists(String accountName) {
        return accountRepository.findByAccountName(accountName) != null;
    }

    // Tìm kiếm tài khoản theo tên người dùng
    public AccountEntity findByAccountName(String accountName) {
        return accountRepository.findByAccountName(accountName);
    }

    // Tìm kiếm tài khoản theo email
    public AccountEntity findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    // Gửi email khôi phục mật khẩu
    public void sendResetPasswordLink(String email) throws Exception {
        AccountEntity account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new Exception("Email không tồn tại trong hệ thống.");
        }

        // Tạo token khôi phục mật khẩu
        String token = UUID.randomUUID().toString();
        account.setResetToken(token);
        accountRepository.save(account);  // Lưu token vào cơ sở dữ liệu

        // Gửi email chứa link đặt lại mật khẩu
        String resetUrl = "http://localhost:8080/account/reset-password?token=" + token;
        emailService.sendEmail(account.getEmail(), "Đặt lại mật khẩu", "Click vào link để đặt lại mật khẩu: " + resetUrl);
    }

    // Cập nhật mật khẩu mới
    @Transactional
    public void updatePassword(String token, String newPassword) throws Exception {
        AccountEntity account = accountRepository.findByResetToken(token);
        if (account == null) {
            throw new Exception("Token không hợp lệ hoặc đã hết hạn.");
        }

        // Mã hóa mật khẩu mới
        account.setPassword(passwordEncoder.encode(newPassword));
        account.setResetToken(null);  // Xóa token sau khi đặt lại mật khẩu
        accountRepository.save(account);
    }
}
