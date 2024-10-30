package com.example.swp391.service;

import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.entity.StaffEntity;
import com.example.swp391.entity.StaffProjectEntity;
import com.example.swp391.entity.StaffProjectId;
import com.example.swp391.repository.ProjectRepository;
import com.example.swp391.repository.StaffProjectRepository;
import com.example.swp391.repository.StaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StaffProjectService {

    @Autowired
    private StaffProjectRepository projectStaffRepository;
    @Autowired
    private StaffRepository staffRepository; // Thêm repository để tìm kiếm StaffEntity

    @Autowired
    private ProjectRepository projectRepository;
    public void assignStaffToProject(Integer projectId, Integer staffId, String taskDescription, String deadline) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDeadline = LocalDate.parse(deadline, formatter);

        // Get the last record in StaffProject table, if exists
        StaffProjectEntity lastStaffProject = projectStaffRepository.findTopByOrderByStaffProjectIDDesc();

        StaffEntity staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dự án"));

        // Create a new StaffProjectEntity instance
        StaffProjectEntity staffProject = new StaffProjectEntity();

        // Set the new StaffProjectID based on the last ID or start from 1
        int newStaffProjectID;
        if (lastStaffProject != null) {
            newStaffProjectID = lastStaffProject.getStaffProjectID() + 1;
        } else {
            newStaffProjectID = 1;
        }

        // Set the generated StaffProjectID
        staffProject.setStaffProjectID(newStaffProjectID);
        staffProject.setStaff(staff);
        staffProject.setProject(project);
        staffProject.setTask(taskDescription);
        staffProject.setAssignmentDate(parsedDeadline);

        // Save the new staff project assignment
        projectStaffRepository.save(staffProject);
    }

}
