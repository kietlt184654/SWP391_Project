package com.example.swp391.repository;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    CustomerEntity findTopByOrderByCustomerIDDesc();

}
