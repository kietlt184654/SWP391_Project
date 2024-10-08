package com.example.swp391.repository;

import com.example.swp391.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
   AccountEntity findByAccountNameAndPassword(String accountName, String password);


}