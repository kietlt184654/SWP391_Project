package com.example.swp391.repository;

import com.example.swp391.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {

    // Tìm kiếm vật liệu theo tên (nếu cần)
    MaterialEntity findByMaterialName(String materialName);

    // Phương thức tùy chỉnh để giảm số lượng tồn kho của một vật liệu
    @Modifying
    @Query("UPDATE MaterialEntity m SET m.stockQuantity = m.stockQuantity - :quantity WHERE m.materialId = :materialId AND m.stockQuantity >= :quantity")
    void reduceMaterialStock(@Param("materialId") Long materialId, @Param("quantity") int quantity);
    boolean existsByMaterialName(String materialName);
}