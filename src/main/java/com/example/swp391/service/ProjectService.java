package com.example.swp391.service;

import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.entity.StaffEntity;
import com.example.swp391.entity.StaffProjectEntity;
import com.example.swp391.entity.StaffProjectId;
import com.example.swp391.repository.ProjectRepository;

import com.example.swp391.repository.StaffProjectRepository;
import com.example.swp391.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

//    @Autowired
//    private StaffRepository staffRepository;
//
//    @Autowired
//    private StaffProjectRepository staffProjectRepository;

    // Hàm để lấy danh sách tất cả các dự án
    public List<ProjectEntity> findAll() {
        return projectRepository.findAll();
    }

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StaffProjectRepository staffProjectRepository;



//    // Thêm staff vào dự án
//    public void addStaffToProject(Integer staffID, Integer projectID) {
//        // Tìm kiếm staff và project
//        StaffEntity staff = staffRepository.findById(staffID).orElseThrow(() -> new IllegalArgumentException("Invalid staff ID"));
//        ProjectEntity project = projectRepository.findById(projectID).orElseThrow(() -> new IllegalArgumentException("Invalid project ID"));
//
//        // Tạo đối tượng StaffProjectEntity để lưu vào bảng trung gian
//        StaffProjectId staffProjectId = new StaffProjectId(staffID, projectID);
//        StaffProjectEntity staffProject = new StaffProjectEntity(staffProjectId, staff, project, new Date(), null);
//        staffProjectRepository.save(staffProject);
//    }
    public Optional<ProjectEntity> getProjectById(Integer projectId) {
        return projectRepository.findById(projectId);
    }
}
