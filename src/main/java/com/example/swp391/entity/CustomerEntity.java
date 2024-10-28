package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Entity
@Table(name = "Customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

    @Id

    private int customerID;

    @ManyToOne
    @JoinColumn(name = "AccountID")  // Đảm bảo rằng cột AccountID tồn tại trong DB và ánh xạ đúng
    private AccountEntity account;

    private String additionalInfo;
}
