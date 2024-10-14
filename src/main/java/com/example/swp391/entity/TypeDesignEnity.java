//package com.example.swp391.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.AllArgsConstructor;
//
//@Entity
//@Table(name = "TypeDesign") // Tên bảng trong cơ sở dữ liệu
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class TypeDesignEnity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int typeDesignId; // Khóa chính cho bảng TypeDesign
//
//    // Tên của loại thiết kế (ví dụ: Mẫu có sẵn, Thiết kế riêng)
//    @Column(name = "type_design_name", nullable = false)
//    private String typeDesignName;
//
//    // Liên kết ManyToOne với bảng ServiceEntity (mỗi loại thiết kế có thể thuộc về một dịch vụ)
//    @ManyToOne
//    @JoinColumn(name = "service_id", nullable = false) // Đảm bảo tên cột khóa ngoại khớp với cơ sở dữ liệu
//    private ServiceEnity service;
//}
////package com.example.swp391.entity;
////
////import jakarta.persistence.*;
////import lombok.Data;
////import lombok.NoArgsConstructor;
////import lombok.AllArgsConstructor;
////
////@Entity
////@Table(name = "TypeDesign")
////@Data
////@NoArgsConstructor
////@AllArgsConstructor
////public class TypeDesignEnity {
////
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private int typeDesignId;
////
////    @ManyToOne
////    @JoinColumn(name = "serviceId")
////    private ServiceEnity service;
////}
