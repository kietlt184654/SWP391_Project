package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int blogID;

    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private CustomerEntity customer;

    private String title;
    private String imageUrl;
    @Lob
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
