package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DesignMaterialQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MaterialID")
    private MaterialEntity material;

    @Column(name = "QuantityNeeded")
    private int quantityNeeded;

    @ManyToOne
    @JoinColumn(name = "DesignID")
    private DesignEntity design;
}
