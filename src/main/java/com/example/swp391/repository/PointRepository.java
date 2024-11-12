package com.example.swp391.repository;

import com.example.swp391.entity.PointEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, Long>{
    @Query("SELECT COALESCE(SUM(p.points), 0) FROM PointEntity p WHERE p.customer.customerID = :customerId")
    int findTotalPointsByCustomer(@Param("customerId") Long customerId);
}