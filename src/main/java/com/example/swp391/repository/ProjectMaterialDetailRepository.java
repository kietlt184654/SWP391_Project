package com.example.swp391.repository;

import com.example.swp391.entity.ProjectMaterialDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMaterialDetailRepository extends JpaRepository<ProjectMaterialDetailEntity, Long> {

    // Tìm chi tiết vật liệu theo projectId
    List<ProjectMaterialDetailEntity> findByProject_ProjectId(Long projectId);


}
