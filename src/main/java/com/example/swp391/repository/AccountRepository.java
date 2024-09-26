package com.example.swp391.repository;

import com.example.swp391.entity.AccountEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEnity, Integer> {
   AccountEnity findByAccountNameAndPassword (String accountName, String password);
   AccountEnity findByAccountName(String accountName);
   AccountEnity findByAccountEmail(String accountEmail);
   Optional<AccountEnity> findByAccountId (int accountId);

}