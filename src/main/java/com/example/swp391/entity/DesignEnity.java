package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "design") // Bảng trong cơ sở dữ liệu, đảm bảo tên đúng với bảng
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long designId; // ID của thiết kế

    @Column(name = "design_name", nullable = false) // Tên thiết kế
    private String designName;

    @Lob
    private byte[] img; // Hình ảnh của thiết kế

    @Column(name = "description")
    private String description; // Mô tả về thiết kế

    @Column(name = "price", nullable = false)
    private Double price; // Giá của thiết kế

    // Liên kết với bảng TypeDesign để phân biệt mẫu có sẵn và thiết kế riêng
    @ManyToOne
    @JoinColumn(name = "type_design_id", nullable = false) // Tên cột khóa ngoại trong bảng
    private TypeDesignEnity typeDesign;
}
