package com.example.swp391.repository;

import com.example.swp391.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {

    // Tìm các blog theo ID của khách hàng
    List<BlogEntity> findByCustomer_CustomerID(Long customerId);
}