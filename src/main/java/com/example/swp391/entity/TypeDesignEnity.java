package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "TypeDesign")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDesignEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeDesignId;

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private ServiceEnity service;
}
