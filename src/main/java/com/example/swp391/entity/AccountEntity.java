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

    @Column(name = "AccountName", nullable = false)  // Thêm trường AccountName
    private String accountName;

    @Column(name = "Password", nullable = false)  // Tên cột chính xác
    private String password;

    @Column(name = "AccountType")  // Tên cột chính xác
    private String accountTypeID;

    @Column(name = "Email", nullable = false)  // Tên cột chính xác
    private String email;

    @Column(name = "PhoneNumber", nullable = false)  // Tên cột chính xác
    private String phoneNumber;
    @Column(name = "ResetToken")
    private String token;
    @Column(name = "Address")  // Tên cột chính xác
    private String address;

    @Column(name = "Images")
    private String images;
    @Column(name = "Status", nullable = false)  // Thêm trường Status
    private Boolean status;  // Sử dụng Boolean cho kiểu BIT

}