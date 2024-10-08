package com.example.swp391.repository;

import com.example.swp391.entity.AccountEnity;
import com.example.swp391.entity.DesignEnity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignRepository extends JpaRepository<DesignEnity, String> {


}
