package com.example.swp391.service;

import com.example.swp391.entity.*;
import com.example.swp391.repository.ProjectRepository;
import com.example.swp391.repository.StaffProjectRepository;
import com.example.swp391.repository.StaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffProjectService {

    @Autowired
    private StaffProjectRepository projectStaffRepository;
    @Autowired
    private StaffRepository staffRepository; // Thêm repository để tìm kiếm StaffEntity
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private StaffProjectRepository staffProjectRepository;
    @Autowired
    private ProjectService projectService;
    public List<String> getProgressImages(int staffProjectID) {
        return staffProjectRepository.findProgressImagesByStaffProjectID(staffProjectID);
    }

public void deleteStaffProjectById(int id) {
    staffProjectRepository.deleteById(id);
}
    public void assignStaffToProject(Long projectId, Integer staffId, String taskDescription, String deadline, String status) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDeadline = LocalDate.parse(deadline, formatter);

        // Get the last record in StaffProject table, if exists
        StaffProjectEntity lastStaffProject = projectStaffRepository.findTopByOrderByStaffProjectIDDesc();

        StaffEntity staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Create a new StaffProjectEntity instance
        StaffProjectEntity staffProject = new StaffProjectEntity();

        // Set the new StaffProjectID based on the last ID or start from 1
        int newStaffProjectID = (lastStaffProject != null) ? lastStaffProject.getStaffProjectID() + 1 : 1;

        // Set fields on staffProject
        staffProject.setStaffProjectID(newStaffProjectID);
        staffProject.setStaff(staff);
        staffProject.setProject(project);
        staffProject.setTask(taskDescription);
        staffProject.setAssignmentDate(parsedDeadline);
        staffProject.setStatus(status);


        // Save the new staff project assignment
        projectStaffRepository.save(staffProject);

        // Send notification email (if applicable)
        sendAssignmentEmail(staff.getAccount().getEmail(), project.getName(), taskDescription, deadline);
    }

    private void sendAssignmentEmail(String email, String projectName, String taskDescription, String deadline) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("You have been assigned to a new project: " + projectName);
        message.setText("Dear Staff,\n\nYou have been assigned to the project: " + projectName +
                ".\n\nTask: " + taskDescription + "\nDeadline: " + deadline +
                "\n\nPlease check your account for more details.\n\nBest regards,\nProject Management Team");

        mailSender.send(message);
    }
    public List<StaffProjectEntity> getTasksByProjectId(Long projectId) {
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
    @Transactional
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
    public StaffProjectEntity findByProjectId(Integer staffProjectID) {
        return staffProjectRepository.findById(staffProjectID)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy StaffProject"));
    }

        // Hàm để cập nhật trạng thái của StaffProject
        @Transactional
        public void updateProgressImage(Integer staffProjectID, String imagePath) {
            StaffProjectEntity staffProject = staffProjectRepository.findById(staffProjectID)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy dự án của nhân viên"));
            staffProject.setProgressImage(imagePath);
            staffProjectRepository.save(staffProject);
        }
        public void save(StaffProjectEntity staffProject) {
            projectStaffRepository.save(staffProject);
        }
        public StaffProjectEntity findById(int id) {
            return projectStaffRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy StaffProject"));
        }
    public void deleteImage(Long projectID, String imagePath) {
        // Find the StaffProjectEntity by project ID and image path
        Optional<StaffProjectEntity> staffProjectOptional = staffProjectRepository.findByProject_ProjectIDAndProgressImage(projectID, imagePath);

        if (staffProjectOptional.isPresent()) {
            StaffProjectEntity staffProject = staffProjectOptional.get();

            // Set the ProgressImage field to null to remove the reference
            staffProject.setProgressImage(null);

            // Save the updated entity back to the database
            staffProjectRepository.save(staffProject);
        } else {
            throw new RuntimeException("Image not found for Project ID: " + projectID + " and Image Path: " + imagePath);
        }
    }

//    public List<String> getProgressImagesByProjectId(Long projectId) {
//        // Lấy danh sách các StaffProjectEntity từ projectId
//        List<StaffProjectEntity> tasks = staffProjectRepository.findByProject_ProjectID(projectId);
//
//        // Trích xuất danh sách các hình ảnh (progressImage)
//        return tasks.stream()
//                .map(StaffProjectEntity::getProgressImage) // Lấy cột progressImage
//                .filter(image -> image != null && !image.isEmpty()) // Loại bỏ giá trị null hoặc chuỗi rỗng
//                .collect(Collectors.toList());
//    }

    public List<StaffProjectEntity> getProgressImagesByProjectId(Long projectId) {


        return staffProjectRepository.findByProject_ProjectID(projectId);
    }
    public boolean removeProgressImage(Integer staffProjectID) {
        StaffProjectEntity staffProject = staffProjectRepository.findById(staffProjectID).orElse(null);

        if (staffProject != null) {
            staffProject.setProgressImage(null); // Remove the image URL
            staffProjectRepository.save(staffProject);
            return true;
        }
        return false;
    }
}

