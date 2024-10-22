//package com.example.swp391.service;
//
//import com.example.swp391.entity.ProjectEntity;
//import com.example.swp391.repository.ProjectRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ProjectService {
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    // Tìm kiếm dự án theo ID
//    public Optional<ProjectEntity> findProjectById(Long projectId) {
//        return projectRepository.findById(projectId);
//    }
//
//    // Lấy tất cả các dự án
//    public List<ProjectEntity> getAllProjects() {
//        return projectRepository.findAll();
//    }
//
//    // Lưu dự án (thêm mới hoặc cập nhật)
//    public ProjectEntity saveProject(ProjectEntity project) {
//        return projectRepository.save(project);
//    }
//
//    // Cập nhật dự án
//    public void updateProject(ProjectEntity project) {
//        projectRepository.save(project);  // Cập nhật dự án
//    }
//
//    // Xóa dự án theo ID
//    public void deleteProjectById(Long projectId) {
//        if (projectRepository.existsById(projectId)) {
//            projectRepository.deleteById(projectId);
//        }
//    }
//}
