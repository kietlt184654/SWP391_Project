package com.example.swp391.repository;

import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {



    List<ProjectEntity> findByCustomerCustomerID(Long customerId);
    List<ProjectEntity> findByCustomer_CustomerIDAndStatus(Long customerId, String status);
    List<ProjectEntity> findByCustomer_CustomerID(Long customerId);
    List<ProjectEntity> findByCustomer_CustomerIDAndStatusNot(Long customerId, String status);
    @Query("SELECT p FROM ProjectEntity p LEFT JOIN FETCH p.feedback")
    List<ProjectEntity> findAllProjectsWithFeedback();
}