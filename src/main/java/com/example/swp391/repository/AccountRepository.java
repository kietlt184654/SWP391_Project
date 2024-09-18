package com.example.swp391.repository;

import com.example.swp391.entity.AccountEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEnity, String> {
    AccountEnity findByUserName(String username);
}