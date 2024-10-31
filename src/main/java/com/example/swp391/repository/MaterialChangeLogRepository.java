package com.example.swp391.repository;

import com.example.swp391.entity.MaterialChangeLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialChangeLogRepository extends JpaRepository<MaterialChangeLogEntity, Long> {
// Tìm lịch sử chỉnh sửa theo materialId
    List<MaterialChangeLogEntity> findByMaterial_MaterialId(Long materialId);
}