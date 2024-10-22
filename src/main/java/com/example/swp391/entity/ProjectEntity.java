package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "Project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;
    private String name;
    private String description; // Mô tả dự án
    private double totalCost;
    @ManyToOne
    @JoinColumn(name = "formRequestId")
    private FormRequestEntity formRequest;

    @ManyToOne
    @JoinColumn(name = "designId")
    private DesignEntity design;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private CustomerEntity customerId;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String status;
}
