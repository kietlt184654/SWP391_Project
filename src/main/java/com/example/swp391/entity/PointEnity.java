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
public class PointEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointId;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private CustomerEnity customer;

    private int orderId;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private ProjectEnity project;

    private int points;
}
