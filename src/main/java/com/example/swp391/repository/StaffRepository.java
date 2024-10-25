//package com.example.swp391.repository;
//
//import com.example.swp391.entity.StaffEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface StaffRepository extends JpaRepository<StaffEntity, Integer> {
//    @Query("SELECT s FROM StaffEntity s WHERE s.staffID NOT IN (SELECT sp.id.staffID FROM StaffProjectEntity sp WHERE sp.id.projectID = :projectId)")
//    List<StaffEntity> findAvailableStaffs(@Param("projectId") Integer projectId);
//}
