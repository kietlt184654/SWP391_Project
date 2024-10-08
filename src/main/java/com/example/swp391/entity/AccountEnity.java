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
public class AccountEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private int accountId;
    @Column(name = "Password")
    private String password;

    // Đảm bảo rằng accountTypeID là Integer
    @Column(name = "AccountTypeID")
    private Integer accountTypeID;

    @Column(name = "AccountName")
    private String accountName;

    @Column(name = "Email")
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "Address")
    private String address;


    @Lob
    @Column(name = "Images")
    private byte[] images;
//    @Column(name = "AccountName")
//    private String status;
}

