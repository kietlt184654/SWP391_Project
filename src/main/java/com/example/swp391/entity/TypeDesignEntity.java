package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

    // Liên kết ManyToOne với bảng ServiceEntity (mỗi loại thiết kế có thể thuộc về một dịch vụ)
    @ManyToOne
    @JoinColumn(name = "serviceId", nullable = false) // Đảm bảo tên cột khóa ngoại khớp với cơ sở dữ liệu
    private ServiceEntity service;
}
//package com.example.swp391.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.AllArgsConstructor;
//
//@Entity
//@Table(name = "TypeDesign")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class TypeDesignEnity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int typeDesignId;
//
//    @ManyToOne
//    @JoinColumn(name = "serviceId")
//    private ServiceEnity service;
//}
