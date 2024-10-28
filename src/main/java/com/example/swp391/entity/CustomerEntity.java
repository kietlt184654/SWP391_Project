package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "Customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private long customerID;

    @OneToOne
    @JoinColumn(name = "AccountID")  // Đảm bảo rằng cột AccountID tồn tại trong DB và ánh xạ đúng
    private AccountEntity account;

    private String additionalInfo;
}
