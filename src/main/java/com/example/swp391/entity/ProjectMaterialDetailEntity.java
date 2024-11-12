package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "ProjectMaterialDetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMaterialDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectMaterialDetailId")
    private long projectMaterialDetailId;

    @Column(name = "QuantityUsed")
    private int quantityUsed;

    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false)
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "MaterialID", nullable = false)
    private MaterialEntity material;

    @Temporal(TemporalType.DATE)
    @Column(name = "UsedDate")
    private Date usedDate;
}