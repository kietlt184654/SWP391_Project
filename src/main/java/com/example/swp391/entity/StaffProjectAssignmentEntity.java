package com.example.swp391.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields
public class StaffProjectAssignmentEntity {
    private String accountName;          // From AccountEntity
    private String projectName;          // From ProjectEntity
    private String projectDescription;   // From ProjectEntity
    private String status;               // From StaffProjectEntity
    private LocalDate endDate;

}
