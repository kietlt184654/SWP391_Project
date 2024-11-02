package com.example.swp391.service;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.entity.StaffEntity;
import com.example.swp391.repository.AccountRepository;
import com.example.swp391.repository.CustomerRepository;
import com.example.swp391.repository.StaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private StaffRepository staffRepository;
private EmailService emailService;

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
        // Tìm tài khoản có AccountID lớn nhất hiện có
        AccountEntity lastAccount = accountRepository.findTopByOrderByAccountIdDesc();

        if (lastAccount != null) {
            // Tăng giá trị AccountID thủ công
            userDTO.setAccountId(lastAccount.getAccountId() + 1);
        } else {
            // Nếu bảng trống, bắt đầu từ AccountID = 1
            userDTO.setAccountId(1);
        }

        // Thiết lập các giá trị khác
        userDTO.setAccountTypeID("Customer");
        userDTO.setStatus(true);

        // Lưu tài khoản vào cơ sở dữ liệu
        accountRepository.save(userDTO);
        // Sau khi lưu xong tài khoản, tạo một Customer mới
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerID(customerRepository.findTopByOrderByCustomerIDDesc().getCustomerID() + 1); // Gán AccountID mới tạo cho Customer
        customer.setAdditionalInfo("Thông tin khách hàng mặc định"); // Thông tin thêm cho khách hàng, có thể thay đổi
        customer.setAccount(userDTO); // Sửa đổi: gán trực tiếp đối tượng AccountEntity
        customerRepository.save(customer); // Lưu thông tin Customer vào cơ sở dữ liệu
    }


    public AccountEntity findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public void updatePassword(AccountEntity user, String newPassword) {
        // Không mã hóa, chỉ cập nhật mật khẩu trực tiếp
        user.setPassword(newPassword);

        // Lưu đối tượng user đã cập nhật vào cơ sở dữ liệu
        accountRepository.save(user);
    }

    public AccountEntity createAccount(AccountEntity account) {
        // Validate account details before saving
        if (account == null || account.getEmail() == null || account.getAccountName() == null) {
            throw new IllegalArgumentException("Account details are incomplete or null");
        }
        AccountEntity lastAccount = accountRepository.findTopByOrderByAccountIdDesc();

        if (lastAccount != null) {
            // Tăng giá trị AccountID thủ công
            account.setAccountId(lastAccount.getAccountId() + 1);
        } else {
            // Nếu bảng trống, bắt đầu từ AccountID = 1
            account.setAccountId(1);
        }

        return accountRepository.save(account);
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