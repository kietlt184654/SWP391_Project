package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "DesignTemplate") // Tên bảng trong SQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DesignTemplateID")
    private Long designId; // IDENTITY trong SQL nên dùng kiểu Long

    @Column(name = "DesignName", nullable = false, length = 100)
    private String designName; // Tên thiết kế

    @Column(name = "Type", nullable = false, length = 50)
    private String designType; // Loại thiết kế

    @Column(name = "Description", length = 255)
    private String description; // Mô tả thiết kế

    @Column(name = "Img", length = 255)
    private String img; // Đường dẫn tới ảnh

    @Column(name = "Size", nullable = false, length = 50)
    @Enumerated(EnumType.STRING) // Đảm bảo chỉ nhận 'Small', 'Medium', 'Large'
    private Size size; // Kích cỡ của thiết kế, sử dụng enum

    @Column(name = "Price", nullable = false)
    private double price; // Giá không âm

    @Column(name = "EstimatedCompletionTime", nullable = false)
    private int estimatedCompletionTime; // Số ngày hoàn thành lớn hơn 0

    @Column(name = "Status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING) // Đảm bảo chỉ nhận 'Available', 'Unavailable', 'Pending'
    private Status status; // Trạng thái của thiết kế, sử dụng enum

    // Enum để giới hạn các giá trị cho Size và Status
    public enum Size {
        SMALL, MEDIUM, LARGE
    }

    public enum Status {
        AVAILABLE, UNAVAILABLE, PENDING
    }
}
