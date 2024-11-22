package com.example.swp391.service;

import com.example.swp391.entity.PaymentEntity;
import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Lưu một thanh toán vào cơ sở dữ liệu
    public void savePayment(PaymentEntity payment) {
        paymentRepository.save(payment);
    }
    public PaymentEntity getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }


}