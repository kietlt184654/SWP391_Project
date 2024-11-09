package com.example.swp391.service;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.repository.AccountRepository;
import com.example.swp391.repository.CustomerRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
private EmailService emailService;
    @Autowired
    private CustomerRepository customerRepository;

    public AccountEntity login(String accountName, String password) {
        return accountRepository.findByAccountNameAndPassword(accountName, password);

    }
    public boolean checkIfEmailExists(String email) {
        return accountRepository.findByEmail(email)!=null;
    }

    public boolean checkIfAccountNameExists(String username) {
        return accountRepository.findByAccountName(username)!=null;
    }
    public synchronized void registerUser(AccountEntity userDTO) {
        // Thiết lập các giá trị khác cho tài khoản
        userDTO.setAccountTypeID("Customer");
        userDTO.setStatus(true);

        // Lưu tài khoản vào cơ sở dữ liệu (AccountID sẽ tự động tăng)
        accountRepository.save(userDTO);

        // Tạo một Customer mới và thiết lập thông tin
        CustomerEntity customer = new CustomerEntity();
        customer.setAdditionalInfo("Thông tin khách hàng mặc định"); // Thông tin thêm cho khách hàng, có thể thay đổi
        customer.setAccount(userDTO); // Gán đối tượng AccountEntity đã lưu vào Customer
        customerRepository.save(customer); // Lưu Customer (CustomerID sẽ tự động tăng)
    }




    public AccountEntity findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }


    public AccountEntity save(AccountEntity account) {
        return accountRepository.save(account);
    }
    public void updatePassword(AccountEntity user, String newPassword) {
        // Không mã hóa, chỉ cập nhật mật khẩu trực tiếp
        user.setPassword(newPassword);

        // Lưu đối tượng user đã cập nhật vào cơ sở dữ liệu
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
    public AccountEntity getCurrentAccount(HttpSession session) {
        return (AccountEntity) session.getAttribute("loggedInUser");
    }
    public boolean checkIfEmailExistsAndSendResetLink(String email) {
        AccountEntity user = accountRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        String token = UUID.randomUUID().toString();
        user.setToken(token);
        accountRepository.save(user);

        String resetUrl = "http://localhost:8080/account/reset-password?token=" + token;
        sendResetPasswordEmail(email, resetUrl);

        return true;
    }

    private void sendResetPasswordEmail(String email, String resetUrl) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Reset Your Password");

            String content = "<p>Hello,</p>"
                    + "<p>We received a request to reset your password. "
                    + "If you requested this, please click the link below to reset your password:</p>"
                    + "<p><a href=\"" + resetUrl + "\">Reset Password</a></p>"
                    + "<br>"
                    + "<p>If you did not request a password reset, please ignore this email.</p>"
                    + "<p>This link will expire in 15 minutes for your security.</p>"
                    + "<br>"
                    + "<p>Best regards,<br>Your Company Team</p>";

            helper.setText(content, true); // Set to true to enable HTML formatting

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    // Phương thức kiểm tra token có tồn tại hay không
    public boolean isValidResetToken(String token) {
        return accountRepository.findByToken(token) != null;
    }

    // Phương thức đặt lại mật khẩu mới nếu token hợp lệ
    public boolean resetPassword(String token, String newPassword) {
        AccountEntity account = accountRepository.findByToken(token);
        if (account != null) {
            account.setPassword(newPassword); // Đảm bảo mã hóa mật khẩu nếu cần
            account.setToken(null); // Xóa token sau khi sử dụng
            accountRepository.save(account);
            return true;
        }
        return false;
    }
}