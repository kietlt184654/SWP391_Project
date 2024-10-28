package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "Project")
@Data                         // Tự động sinh getter, setter, toString, hashCode, equals
@NoArgsConstructor             // Tự động sinh constructor không tham số
@AllArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectID;  // Khóa chính

    private String description;
    private LocalDate endDate;  // Sử dụng LocalDate hoặc java.sql.Date thay vì java.util.Date nếu chỉ cần ngày
    private String name;
    private LocalDate startDate;
    private String status;
    private Float totalCost;
    private Long designId;
    private Integer formRequestId;
    private String priority;
    private String img;
//    @OneToMany(mappedBy = "project")
//    private List<StaffProjectEntity> staffProjects;
}
