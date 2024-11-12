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
        return accountRepository.findByEmail(email) != null;
    }

    public boolean checkIfAccountNameExists(String username) {
        return accountRepository.findByAccountName(username) != null;
    }

    public synchronized void registerUser(AccountEntity userDTO) {
        // Set other values for the account
        userDTO.setAccountTypeID("Customer");
        userDTO.setStatus(true);

        // Save the account into the database (AccountID will auto-increment)
        accountRepository.save(userDTO);

        // Create a new Customer and set information
        CustomerEntity customer = new CustomerEntity();
        customer.setAdditionalInfo("Default customer information"); // Default additional info, can be changed
        customer.setAccount(userDTO); // Link the saved AccountEntity to Customer
        customerRepository.save(customer); // Save Customer (CustomerID will auto-increment)
    }

    public AccountEntity findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public AccountEntity save(AccountEntity account) {
        return accountRepository.save(account);
    }

    public void updatePassword(AccountEntity user, String newPassword) {
        // No encryption, directly update the password
        user.setPassword(newPassword);

        // Save the updated user object into the database
        accountRepository.save(user);
    }

    public AccountEntity findByToken(String token) {
        return accountRepository.findByToken(token); // Find by token
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
        // Search for the user by email
        AccountEntity account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new Exception("Email does not exist in the system.");
        }

        // Create a password reset token
        String token = UUID.randomUUID().toString();
        account.setToken(token);  // Save the token into the account object
        accountRepository.save(account);  // Save the account with the new token

        // Create a password reset URL
        String resetUrl = "http://localhost:8080/account/reset-password?token=" + token;

        // Send the reset password email with the link
        emailService.sendEmail(account.getEmail(), "Password Reset",
                "Click the following link to reset your password: " + resetUrl);
    }

    // Update password based on the reset token
    @Transactional
    public void updatePassword(String token, String newPassword) throws Exception {
        AccountEntity account = accountRepository.findByToken(token);
        if (account == null) {
            throw new Exception("Invalid or expired token.");
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

    // Method to check if the token exists
    public boolean isValidResetToken(String token) {
        return accountRepository.findByToken(token) != null;
    }

    // Method to reset password if the token is valid
    public boolean resetPassword(String token, String newPassword) {
        AccountEntity account = accountRepository.findByToken(token);
        if (account != null) {
            account.setPassword(newPassword); // Ensure password is encrypted if necessary
            account.setToken(null); // Remove token after use
            accountRepository.save(account);
            return true;
        }
        return false;
    }
}
