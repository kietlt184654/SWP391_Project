package com.example.swp391.service;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
private EmailService emailService;

    public AccountEntity login(String accountName, String password) {
        return accountRepository.findByAccountNameAndPassword(accountName, password);

    }
    public boolean checkIfEmailExists(String email) {
        return accountRepository.findByEmail(email)!=null;
    }

    public boolean checkIfAccountNameExists(String username) {
        return accountRepository.findByAccountName(username)!=null;
    }
    public void registerUser(AccountEntity userDTO) {
        userDTO.setAccountId(accountRepository.findTopByOrderByAccountIdDesc().getAccountId()+1);
        userDTO.setAccountTypeID("Customer");
        userDTO.setStatus(true);
        accountRepository.save(userDTO);
    }
    public AccountEntity findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
    //    public void updateResetToken(String token, String email) {
//        AccountEntity user = accountRepository.findByEmail(email);
//        if (user != null) {
//            user.setResetToken(token);
//            accountRepository.save(user);
//        }
//
//
//    }
    public void updatePassword(AccountEntity user, String newPassword) {
        // Không mã hóa, chỉ cập nhật mật khẩu trực tiếp
        user.setPassword(newPassword);

        // Lưu đối tượng user đã cập nhật vào cơ sở dữ liệu
        accountRepository.save(user);
    }

    public void save(AccountEntity user) {
        accountRepository.save(user);
    }


    public AccountEntity findByToken(String token) {
        return accountRepository.findByToken(token); // Tìm theo token
    }


    public void updateAccount(AccountEntity account) {
        accountRepository.save(account);
    }
    public List<AccountEntity> getAllCustomers() {
        return accountRepository.findAll();
    }
    public long countUsers() {
        return accountRepository.count();
    }
    @Transactional
    public void sendResetPasswordLink(String email) throws Exception {
        // Tìm kiếm người dùng theo email
        AccountEntity account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new Exception("Email không tồn tại trong hệ thống.");
        }

        // Tạo token khôi phục mật khẩu
        String token = UUID.randomUUID().toString();
        account.setToken(token);  // Lưu token vào đối tượng tài khoản
        accountRepository.save(account);  // Lưu tài khoản với token mới

        // Tạo URL khôi phục mật khẩu
        String resetUrl = "http://localhost:8080/account/reset-password?token=" + token;

        // Gửi email chứa liên kết khôi phục mật khẩu
        emailService.sendEmail(account.getEmail(), "Khôi phục mật khẩu",
                "Nhấp vào liên kết sau để đặt lại mật khẩu của bạn: " + resetUrl);
    }

    // Cập nhật mật khẩu mới dựa trên token khôi phục
    @Transactional
    public void updatePassword(String token, String newPassword) throws Exception {
        AccountEntity account = accountRepository.findByToken(token);
        if (account == null) {
            throw new Exception("Token không hợp lệ hoặc đã hết hạn.");
        }

    }
}