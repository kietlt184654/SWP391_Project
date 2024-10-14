package com.example.swp391.repository;

import com.example.swp391.entity.ProjectEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEnity, Long> {

    // Tìm dự án theo tên (nếu cần)
    List<ProjectEnity> findByNameContaining(String name);
}
