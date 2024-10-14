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
public class CustomerEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerID;

    @ManyToOne
    @JoinColumn(name = "AccountID")
    private AccountEnity account;

    private String additionalInfor;
}
