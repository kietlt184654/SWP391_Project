package com.example.swp391.repository;

import com.example.swp391.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    // Tìm dự án theo ID (phương thức mặc định từ JpaRepository)
    Optional<ProjectEntity> findById(Long projectId);

    // Lấy tất cả dự án (phương thức mặc định từ JpaRepository)
    List<ProjectEntity> findAll();

    // Xóa dự án theo ID (phương thức mặc định từ JpaRepository)
    void deleteById(Long projectId);

    // Thêm truy vấn tùy chỉnh: Tìm dự án theo tên
    List<ProjectEntity> findByNameContainingIgnoreCase(String name);

    // Thêm truy vấn tùy chỉnh: Tìm dự án theo trạng thái
    List<ProjectEntity> findByStatus(String status);
}
