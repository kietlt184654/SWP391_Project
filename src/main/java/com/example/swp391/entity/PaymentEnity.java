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
public class PaymentEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId; // ID của bảng Payment

    private String transactionId; // Mã giao dịch trả về từ VNPay
    private double amount; // Số tiền thanh toán

    private Date paymentDate; // Ngày thanh toán
    private String paymentStatus; // Trạng thái thanh toán (ví dụ: "Success", "Failed")

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private CustomerEnity customer; // Liên kết với bảng Customer (khách hàng thực hiện thanh toán)

    @ManyToOne
    @JoinColumn(name = "projectId", nullable = false)
    private ProjectEnity project; // Liên kết với bảng Project (dự án tương ứng với thanh toán)
}
