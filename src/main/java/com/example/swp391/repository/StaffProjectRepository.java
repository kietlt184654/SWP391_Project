package com.example.swp391.repository;

import com.example.swp391.entity.StaffProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StaffProjectRepository extends JpaRepository<StaffProjectEntity, Integer> {

    // Lấy tất cả các nhiệm vụ dựa trên ProjectID
    List<StaffProjectEntity> findByProjectProjectID(Integer projectId);

    // Lấy tất cả các nhiệm vụ dựa trên StaffID (nếu cần)
    List<StaffProjectEntity> findByStaffStaffID(Integer staffId);
}
