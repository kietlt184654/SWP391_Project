package com.example.swp391.repository;

import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {


    // Tìm dự án theo customerId
    List<ProjectEntity> findByCustomerCustomerID(Long customerId);
}
