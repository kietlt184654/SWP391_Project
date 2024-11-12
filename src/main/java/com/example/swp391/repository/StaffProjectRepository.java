package com.example.swp391.repository;

import com.example.swp391.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffProjectRepository extends JpaRepository<StaffProjectEntity, Integer> {
    StaffProjectEntity findTopByOrderByStaffProjectIDDesc();
    List<StaffProjectEntity> findByProject_ProjectID(Long projectID);
List<StaffProjectEntity> findByStatus(String status);
    @Query("SELECT COUNT(sp) FROM StaffProjectEntity sp WHERE sp.project.projectID = :projectId AND sp.status <> 'Done'")
    int countIncompleteTasks(@Param("projectId") Integer projectId);
    List<StaffProjectEntity> findByStaff_StaffID(int staffId);
    Optional<StaffProjectEntity> findByProject_ProjectIDAndStaff_StaffID(Long projectId, int staffId);
    StaffProjectEntity findByStaffProjectID(int staffProjectID);
    // Query to get the progress image URLs for a given staffProjectID
    @Query("SELECT sp.progressImage FROM StaffProjectEntity sp WHERE sp.staffProjectID = :staffProjectID")
    List<String> findProgressImagesByStaffProjectID(int staffProjectID);
    Optional<StaffProjectEntity> findByProject_ProjectIDAndProgressImage(Long projectID, String progressImage);
}
