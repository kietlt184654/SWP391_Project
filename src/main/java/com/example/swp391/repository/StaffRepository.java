package com.example.swp391.repository;

import com.example.swp391.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Integer> {
    List<StaffEntity> findByRole(String role);
}
