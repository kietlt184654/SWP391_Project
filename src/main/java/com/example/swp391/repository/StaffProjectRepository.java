package com.example.swp391.repository;

import com.example.swp391.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StaffProjectRepository extends JpaRepository<StaffProjectEntity, Integer> {
    StaffProjectEntity findTopByOrderByStaffProjectIDDesc();
    List<StaffProjectEntity> findByProject_ProjectID(Integer projectID);
List<StaffProjectEntity> findByStatus(String status);

    List<StaffProjectEntity> findByStaff_StaffID(int staffId);
}
