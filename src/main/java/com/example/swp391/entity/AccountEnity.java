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
    private int accountId;

    private String password;
    private String accountType;
    private String accountName;
    private String email;
    private String phoneNumber;
    private String address;

    @Lob
    private byte[] images;

    private String status;
}

