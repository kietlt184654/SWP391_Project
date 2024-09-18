package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Service")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceId;

    private String row1;
    private String row2;
    private String row3;
}
