package com.example.swp391.repository;

import com.example.swp391.entity.DesignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignRepository extends JpaRepository<DesignEntity, Long> {

    /**
     * Tìm kiếm thiết kế dựa trên tên chính xác.
     *
     * @param designName Tên thiết kế cần tìm kiếm.
     * @return Danh sách các thiết kế phù hợp.
     */
    List<DesignEntity> findByDesignName(String designName);

    /**
     * Tìm kiếm các thiết kế có tên gần giống và thuộc loại thiết kế cụ thể.
     *
     * @param designName Tên thiết kế cần tìm kiếm (không phân biệt hoa thường).
     * @param typeDesignId ID của kiểu thiết kế cần tìm.
     * @return Danh sách các thiết kế phù hợp.
     */
    List<DesignEntity> findByDesignNameContainingIgnoreCaseAndTypeDesignId_typeDesignId(String designName, long typeDesignId);

}
