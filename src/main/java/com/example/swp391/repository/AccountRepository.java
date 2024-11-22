package com.example.swp391.repository;

import com.example.swp391.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
   AccountEntity findByAccountNameAndPassword(String accountName, String password);
   AccountEntity findByAccountName(String accountName);

   AccountEntity findTopByOrderByAccountIdDesc();
   AccountEntity findByEmail(String email);
   //AccountEntity findByEmail(String email);
   // Tìm kiếm tài khoản theo token khôi phục mật khẩu
   AccountEntity findByToken(String token);
   Optional<AccountEntity> findByPhoneNumber(String phoneNumber);

}