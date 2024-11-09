package com.example.swp391.repository;

import com.example.swp391.entity.DesignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignRepository extends JpaRepository<DesignEntity, Long> {
    List<DesignEntity> findByTypeDesign_TypeDesignId(long typeDesignId);
    List<DesignEntity> findByTypeDesign_TypeDesignIdAndSizeAndPriceLessThanEqualAndEstimatedCompletionTimeLessThanEqual(
            long typeDesignId, DesignEntity.Size size, double price, int estimatedCompletionTime);
    /**
     * Tìm tất cả các thiết kế với trạng thái cụ thể
     * @param status Trạng thái của thiết kế
     * @return Danh sách các DesignEntity có status được chỉ định
     */
    List<DesignEntity> findByStatus(DesignEntity.Status status);
    // Phương thức để tìm kiếm thiết kế theo trạng thái và TypeDesignId
    List<DesignEntity> findByStatusAndTypeDesign_TypeDesignId(DesignEntity.Status status, long typeDesignId);
    List<DesignEntity> findByCustomerReference(Long customerReference);
}