package com.example.swp391.repository;

import com.example.swp391.entity.ProjectMaterialDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMaterialDetailRepository extends JpaRepository<ProjectMaterialDetailEntity, Long> {

    // Tìm danh sách vật liệu sử dụng theo ID của dự án
    List<ProjectMaterialDetailEntity> findByProject_ProjectId(Long projectId);
}
