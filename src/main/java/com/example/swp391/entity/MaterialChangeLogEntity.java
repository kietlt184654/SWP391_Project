package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "MaterialChangeLog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialChangeLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MaterialID", nullable = false)
    private MaterialEntity material;

    @Column(name = "QuantityChanged")
    private int quantityChanged;

    @Column(name = "Reason")
    private String reason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ChangeDate")
    private Date changeDate;
}
