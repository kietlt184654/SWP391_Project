package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Project_Material_Detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMaterialDetailEnity { // Sửa lại tên lớp cho đúng

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projectMaterialDetailId; // ID của bảng này

    private int quantity; // Số lượng vật liệu sử dụng trong dự án

    // Liên kết với bảng Project
    @ManyToOne
    @JoinColumn(name = "projectId", nullable = false) // Tên cột trong bảng Project_Material_Detail
    private ProjectEnity project; // Sửa lại tên thành ProjectEntity cho đúng

    // Liên kết với bảng Material
    @ManyToOne
    @JoinColumn(name = "materialId", nullable = false) // Tên cột trong bảng Project_Material_Detail
    private MaterialEnity material; // Sửa lại tên thành MaterialEntity cho đúng
}
