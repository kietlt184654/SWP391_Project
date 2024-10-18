package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")  // Tên cột chính xác
    private int accountId;

    @Column(name = "AccountName", nullable = false, length = 100)  // Thêm trường AccountName
    private String accountName;

    @Column(name = "Password", nullable = false, length = 255)  // Mã hóa mật khẩu nên để đủ dài
    private String password;

    @Column(name = "AccountType", length = 50)  // Ánh xạ cột chính xác
    private String accountTypeID;

    @Column(name = "Email", nullable = false, unique = true, length = 100)  // Email cần duy nhất
    private String email;

    @Column(name = "PhoneNumber", nullable = false, length = 15)  // Tối đa 15 ký tự cho số điện thoại quốc tế
    private String phoneNumber;

    @Column(name = "ResetToken", length = 100)  // Thêm trường reset token
    private String resetToken;

    @Column(name = "Address", length = 255)  // Thêm cột địa chỉ
    private String address;

    @Column(name = "Images", length = 255)  // Lưu link hoặc tên hình ảnh
    private String images;

    @Column(name = "Status", nullable = false)  // Thêm trạng thái tài khoản
    private Boolean status;  // Sử dụng Boolean cho kiểu BIT/TINYINT
}
