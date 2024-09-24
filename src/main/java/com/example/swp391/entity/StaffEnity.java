package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Staff")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int staffId;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private AccountEnity account;

    private String staffType;
}
