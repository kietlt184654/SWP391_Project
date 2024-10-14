package com.example.swp391.repository;

import com.example.swp391.entity.ProjectMaterialDetailEnity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMaterialDetailRepository extends JpaRepository<ProjectMaterialDetailEnity, Long> {

    // Tìm danh sách vật liệu sử dụng theo ID của dự án
    List<ProjectMaterialDetailEnity> findByProject_ProjectId(Long projectId);
}
