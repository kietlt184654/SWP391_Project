package com.example.swp391.repository;

import com.example.swp391.entity.DesignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    List<DesignEntity> findByCustomerReferenceAndStatus(Long customerReference, DesignEntity.Status status);
    // Lấy tất cả các thiết kế có typeId là 3 hoặc 4
    @Query("SELECT d FROM DesignEntity d WHERE d.typeDesign.typeDesignId = 3L OR d.typeDesign.typeDesignId = 4L ")
    List<DesignEntity> findDesignsByTypeId3Or4();
//    @Query("SELECT t.typeDesignId, COUNT(d) FROM DesignEntity d JOIN d.typeDesign t GROUP BY t.typeDesignId")
//    List<Object[]> countDesignsByTypeId();

//    @Query("SELECT d.typeDesign.typeDesignId, COUNT(d) FROM DesignEntity d GROUP BY d.typeDesign.typeDesignId")
//    List<Object[]> countDesignsByTypeId();

    @Query("SELECT t.typeDesignId, t.typeDesignName, COUNT(d) FROM DesignEntity d JOIN d.typeDesign t GROUP BY t.typeDesignId, t.typeDesignName")
    List<Object[]> countDesignsByTypeIdWithNames();



}