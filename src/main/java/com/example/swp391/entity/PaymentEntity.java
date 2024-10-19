package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "Payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentId", nullable = false, updatable = false)
    private long paymentId; // ID của bảng Payment

    @Column(name = "transactionId", nullable = false, unique = true)
    private String transactionId; // Mã giao dịch trả về từ VNPay, đảm bảo duy nhất

    @Column(name = "amount", nullable = false)
    private double amount; // Số tiền thanh toán, không được để trống

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "paymentDate", nullable = false)
    private Date paymentDate; // Ngày thanh toán, sử dụng kiểu TIMESTAMP để lưu cả ngày và giờ

    @Column(name = "paymentStatus", nullable = false, length = 20)
    private String paymentStatus; // Trạng thái thanh toán (ví dụ: "Success", "Failed"), không để trống và giới hạn độ dài

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = false)
    private CustomerEntity customer; // Liên kết với bảng Customer (khách hàng thực hiện thanh toán)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId", nullable = false)
    private ProjectEntity project; // Liên kết với bảng Project (dự án tương ứng với thanh toán)
}
