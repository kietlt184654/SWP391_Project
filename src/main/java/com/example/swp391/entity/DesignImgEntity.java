//package com.example.swp391.entity;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.AllArgsConstructor;
//
//
//@Entity
//@Table(name = "Design_img")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class DesignImgEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;
//
//    @Column(name = "img_link", nullable = false, length = 255)
//    private String imgLink;
//
//    @ManyToOne
//    @JoinColumn(name = "DesignTemplateID", referencedColumnName = "DesignTemplateID")
//    private DesignEntity designTemplate;
//}
