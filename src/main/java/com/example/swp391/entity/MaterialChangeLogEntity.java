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
public class MaterialChangeLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "materialId", nullable = false)
    private MaterialEntity material;

    private int quantityChanged;  // Số lượng thay đổi (+/-)
    private String reason;  // Lý do thay đổi
    private java.util.Date changeDate;
}
