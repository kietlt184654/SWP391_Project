package com.example.swp391.service;

import com.example.swp391.entity.*;
import com.example.swp391.repository.ProjectRepository;
import com.example.swp391.repository.StaffProjectRepository;
import com.example.swp391.repository.StaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class StaffProjectService {

    @Autowired
    private StaffProjectRepository projectStaffRepository;
    @Autowired
    private StaffRepository staffRepository; // Thêm repository để tìm kiếm StaffEntity

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private StaffProjectRepository staffProjectRepository;

    public void assignStaffToProject(Integer projectId, Integer staffId, String taskDescription, String deadline,String status) {
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
        staffProject.setStatus(status);
        staffProject.setProject(project);
        staffProject.setTask(taskDescription);
        staffProject.setAssignmentDate(parsedDeadline);

        // Save the new staff project assignment
        projectStaffRepository.save(staffProject);
    }
    public List<StaffProjectEntity> getTasksByProjectId(Integer projectId) {
        return staffProjectRepository.findByProject_ProjectID(projectId); // Sử dụng tên phương thức đã sửa
    }
    public List<StaffProjectEntity> getTasksByStatus(String status) {
        return staffProjectRepository.findByStatus(status); // Sử dụng tên phương thức đã sửa
    }
    public List<StaffProjectEntity> getAllTasks() {
        return staffProjectRepository.findAll(); // Sử dụng tên phương thức đã sửa
    }

    // Hàm lấy danh sách các dự án của nhân viên
    public List<StaffProjectEntity> getAssignedProjects(int staffId) {
        return staffProjectRepository.findByStaff_StaffID(staffId);
    }

    public boolean setStatusForStaffProject(int staffProjectId, String newStatus) {
        Optional<StaffProjectEntity> optionalStaffProject = staffProjectRepository.findById(staffProjectId);

        if (optionalStaffProject.isPresent()) {
            StaffProjectEntity staffProject = optionalStaffProject.get();
            staffProject.setStatus(newStatus);
            staffProjectRepository.save(staffProject);
            return true;
        } else {
            return false; // StaffProject không tìm thấy
        }
    }
    // Hàm để cập nhật trạng thái của StaffProject

}

