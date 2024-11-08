package com.example.swp391.repository;

import com.example.swp391.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
   AccountEntity findByAccountNameAndPassword(String accountName, String password);
   AccountEntity findByAccountName(String accountName);

   AccountEntity findTopByOrderByAccountIdDesc();
   AccountEntity findByEmail(String email);
   //AccountEntity findByEmail(String email);
   // Tìm kiếm tài khoản theo token khôi phục mật khẩu
   AccountEntity findByToken(String token);

}