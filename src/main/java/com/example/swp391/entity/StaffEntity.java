package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staffID;  // Khóa chính

    @ManyToOne
    @JoinColumn(name = "accountID")  // Liên kết tới bảng Account
    private AccountEntity account;  // Thực thể liên kết tới Customer/Account

    private String role;  // Vai trò của nhân viên
}
