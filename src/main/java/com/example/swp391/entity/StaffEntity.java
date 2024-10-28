package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Staff")
public class StaffEntity {
    @Id
    @Column(name = "StaffID")
    private Integer staffID;  // Primary Key without auto-increment (ID manually set)

    @ManyToOne
    @JoinColumn(name = "AccountID")
    private AccountEntity account;

    @Column(name = "Role")
    private String role;  // Role of the staff (matching database column name)
}
