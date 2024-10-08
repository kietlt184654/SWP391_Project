package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Design")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String designId;

    private String designName;

    @Lob
    private byte[] img;

    private String description;

    @ManyToOne
    @JoinColumn(name = "typeDesignId")
    private TypeDesignEnity typeDesign;
}
