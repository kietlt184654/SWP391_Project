package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int materialId;

    private String materialName;
    private int stockQuantity;
    private String unit;
    private String status;
}
