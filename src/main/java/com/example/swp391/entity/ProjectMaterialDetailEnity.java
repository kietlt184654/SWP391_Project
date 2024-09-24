package com.example.swp391.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Project_Material_Detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMaterialDetailEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectMaterialDetailId;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private ProjectEnity project;

    @ManyToOne
    @JoinColumn(name = "materialId")
    private MaterialEnity material;
}
