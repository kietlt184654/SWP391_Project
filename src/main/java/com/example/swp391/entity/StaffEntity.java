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
    private Integer staffID;

    @OneToOne
    @JoinColumn(name = "AccountID", referencedColumnName = "AccountID", nullable = false) // Khóa ngoại trỏ đến AccountEntity
    private AccountEntity account;

    @Column(name = "Role")
    private String role;
}
