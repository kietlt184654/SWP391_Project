package com.example.swp391.repository;

import com.example.swp391.entity.CustomerEnity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEnity, String> {

}
