package com.example.swp391.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AccountType")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tạo giá trị cho AccountTypeID
    @Column(name = "AccountTypeID") // Tên cột trong cơ sở dữ liệu
    private int accountTypeID;

    @Column(name = "AccountTypeName", nullable = false) // Không cho phép null
    private String accountTypeName;
}
