package com.example.swp391.repository;

import com.example.swp391.entity.PaymentEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEnity, Long> {

    // Tìm kiếm thanh toán theo transaction ID (nếu cần)
    PaymentEnity findByTransactionId(String transactionId);
}
