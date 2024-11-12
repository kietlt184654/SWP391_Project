package com.example.swp391.repository;

import com.example.swp391.entity.DesignEntity;
import com.example.swp391.entity.DesignMaterialQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignMaterialQuantityRepository extends JpaRepository<DesignMaterialQuantity, Long> {
    List<DesignMaterialQuantity> findByDesign(DesignEntity design);
    // Các phương thức truy vấn tùy chỉnh có thể thêm nếu cần
}