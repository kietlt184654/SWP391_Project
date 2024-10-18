package com.example.swp391.repository;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    Optional<CustomerEntity> findByCustomerId (long  CustomerId);
}
