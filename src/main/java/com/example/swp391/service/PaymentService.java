package com.example.swp391.service;

import com.example.swp391.entity.PaymentEntity;
import com.example.swp391.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Lưu một thanh toán vào cơ sở dữ liệu
    public void savePayment(PaymentEntity payment) {
        paymentRepository.save(payment);
    }

    // Tìm kiếm thanh toán theo ID (nếu cần)
    public PaymentEntity findPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }

    // Xóa một thanh toán (nếu cần)
    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
    public PaymentEntity findPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }
}
