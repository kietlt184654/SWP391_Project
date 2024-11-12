package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")  // Tên cột chính xác
    private long accountId;

    @Column(name = "AccountName", nullable = false)  // Thêm trường AccountName
    private String accountName;

    @Column(name = "Password", nullable = false)  // Tên cột chính xác
    private String password;

    @Column(name = "AccountType")  // Tên cột chính xác
    private String accountTypeID;

    @Column(name = "Email", nullable = false)  // Tên cột chính xác
    private String email;

    @Column(name = "PhoneNumber")  // Tên cột chính xác
    private String phoneNumber;
    @Column(name = "ResetToken")
    private String token;
    @Column(name = "Address")  // Tên cột chính xác
    private String address;

    @Column(name = "Images")
    private String images;
    @Column(name = "Status", nullable = false)  // Thêm trường Status
    private Boolean status;  // Sử dụng Boolean cho kiểu BIT
    // Thiết lập quan hệ một-một với CustomerEntity
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CustomerEntity customer;

    // Thêm liên kết với StaffEntity
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL) // cascade để đồng bộ hóa dữ liệu
    private StaffEntity staff;

//    @OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ChatMessage> sentMessages;

}