package com.example.swp391.repository;

import com.example.swp391.entity.MaterialChangeLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialChangeLogRepository extends JpaRepository<MaterialChangeLogEntity, Long> {
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh nếu cần
}