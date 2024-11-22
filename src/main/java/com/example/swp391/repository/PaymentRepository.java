package com.example.swp391.repository;

import com.example.swp391.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT MONTH(p.paymentDate) AS month, YEAR(p.paymentDate) AS year, SUM(p.amount) AS totalRevenue " +
            "FROM PaymentEntity p " +
            "GROUP BY YEAR(p.paymentDate), MONTH(p.paymentDate) " +
            "ORDER BY CONCAT(YEAR(p.paymentDate), '-', MONTH(p.paymentDate))")
    List<Object[]> findMonthlyRevenue();
    PaymentEntity findByTransactionId(String transactionId);

}