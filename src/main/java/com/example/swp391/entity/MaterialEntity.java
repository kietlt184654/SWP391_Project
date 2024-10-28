//package com.example.swp391.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.AllArgsConstructor;
//
//@Entity
//@Table(name = "Material")
//@Data // Đảm bảo rằng @Data của Lombok sẽ tự động tạo getter cho bạn
//@NoArgsConstructor
//@AllArgsConstructor
//public class MaterialEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int materialId;
//
//    private String materialName;
//    private int stockQuantity;
//    private String unit;
//    private String status;
//
//    // Lombok sẽ tự động tạo phương thức getMaterialId() khi bạn dùng @Data
//}
//
