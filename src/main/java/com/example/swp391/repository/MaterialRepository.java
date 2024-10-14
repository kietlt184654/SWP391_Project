package com.example.swp391.repository;

import com.example.swp391.entity.MaterialEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MaterialRepository extends JpaRepository<MaterialEnity, Long> {

    @Modifying
    @Query("UPDATE MaterialEnity m SET m.stockQuantity = m.stockQuantity - :quantity WHERE m.materialId = :materialId")
    void reduceMaterialStock(@Param("materialId") Long materialId, @Param("quantity") int quantity);

    }

