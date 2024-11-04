package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Point")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long PointId;

    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false) // Liên kết với CustomerEntity
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false) // Liên kết với ProjectEntity
    private ProjectEntity project;

    @Column(name = "Points")
    private int points; // Số điểm tích lũy từ dự án này
}
