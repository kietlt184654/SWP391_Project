package com.example.swp391.repository;

import com.example.swp391.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

   // Tìm kiếm tài khoản theo tên người dùng
   AccountEntity findByAccountName(String accountName);

   // Tìm kiếm tài khoản theo email
   AccountEntity findByEmail(String email);

   // Tìm kiếm tài khoản theo token khôi phục mật khẩu
   AccountEntity findByResetToken(String resetToken);
}
