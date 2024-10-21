package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "FormRequest")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formRequestId;

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private ServiceEntity service;

    @ManyToOne
    @JoinColumn(name = "typeDesignId")
    private TypeDesignEntity typeDesign;
}
