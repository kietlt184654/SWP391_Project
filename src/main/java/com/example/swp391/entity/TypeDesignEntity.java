package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TypeDesign") // Tên bảng trong cơ sở dữ liệu
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDesignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long typeDesignId; // Khóa chính cho bảng TypeDesign

    // Tên của loại thiết kế (ví dụ: Mẫu có sẵn, Thiết kế riêng)
    @Column(name = "TypeDesignName", nullable = false)
    private String typeDesignName;
}
