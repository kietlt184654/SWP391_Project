package com.example.swp391.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;           // Tự động sinh getter, setter, toString, hashCode, equals
import lombok.NoArgsConstructor; // Tạo constructor không tham số
import lombok.AllArgsConstructor; // Tạo constructor với tất cả tham số


import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "StaffProject")
public class StaffProjectEntity {



    @Id

    @Column(name = "StaffProjectID")
    private int staffProjectID;

    @ManyToOne
    @JoinColumn(name = "StaffID", referencedColumnName = "StaffID", nullable = false) // Ensures StaffID cannot be null

    private StaffEntity staff;

    @ManyToOne
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", nullable = false) // Ensures ProjectID cannot be null
    private ProjectEntity project;

    @Column(name = "AssignmentDate")
    private LocalDate assignmentDate; // `LocalDate` for date-only fields in Java

    @Column(name = "ProgressImage")
    private String progressImage;

    @Column(name = "Task")
    private String task;

    @Column(name = "Status")
    private String status;

}
