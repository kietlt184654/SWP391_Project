package com.example.swp391.repository;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.StaffProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StaffProjectRepository extends JpaRepository<StaffProjectEntity, Integer> {
    StaffProjectEntity findTopByOrderByStaffProjectIDDesc();


}
