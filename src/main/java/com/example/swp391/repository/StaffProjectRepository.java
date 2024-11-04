package com.example.swp391.repository;

import com.example.swp391.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffProjectRepository extends JpaRepository<StaffProjectEntity, Integer> {
    StaffProjectEntity findTopByOrderByStaffProjectIDDesc();
    List<StaffProjectEntity> findByProject_ProjectID(Long projectID);
    List<StaffProjectEntity> findByStatus(String status);

    List<StaffProjectEntity> findByStaff_StaffID(int staffId);
    Optional<StaffProjectEntity> findByProject_ProjectIDAndStaff_StaffID(int projectId, int staffId);
    StaffProjectEntity findByStaffProjectID(int staffProjectID);
}