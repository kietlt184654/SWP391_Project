package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectID")
    private int projectID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "TotalCost")
    private double totalCost;

    @ManyToOne
    @JoinColumn(name = "DesignID")
    private DesignEntity design;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private CustomerEntity customer;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "Status")
    private String status;

    private String img;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectMaterialDetailEntity> projectMaterials = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentEntity> payments = new ArrayList<>();
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<PointEntity> points = new ArrayList<>(); // Quan hệ một-nhiều với PointEntity
}
