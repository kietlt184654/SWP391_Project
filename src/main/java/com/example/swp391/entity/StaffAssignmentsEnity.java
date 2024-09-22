package com.example.swp391.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Staff_Assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffAssignmentsEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int staffAssignmentId;

    @ManyToOne
    @JoinColumn(name = "staffId")
    private StaffEnity staff;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private ProjectEnity project;

    private String task;

    @Lob
    private byte[] img;
}
