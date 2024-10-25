package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffEntity {



    @Id

    private Integer staffID;  // Khóa chính

    private Integer accountID;  // Liên kết tới bảng Account
    private String role;

    @OneToMany(mappedBy = "staff")
    private List<StaffProjectEntity> staffProjects;
}
