package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaterialID")
    private long materialId;

    @Column(name = "MaterialName")
    private String materialName;

    @Column(name = "StockQuantity")
    private int stockQuantity;

    @Column(name = "Unit")
    private String unit;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DesignMaterialQuantity> designQuantities = new ArrayList<>();
}
