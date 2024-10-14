package com.example.swp391.service;

import com.example.swp391.entity.ProjectEnity;
import com.example.swp391.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Lấy tất cả dự án
    public List<ProjectEnity> getAllProjects() {
        return projectRepository.findAll();
    }

    // Tìm dự án theo ID
    public ProjectEnity findProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    // Lưu dự án (tạo mới hoặc cập nhật)
    public ProjectEnity saveProject(ProjectEnity project) {
        return projectRepository.save(project);
    }

    // Cập nhật trạng thái dự án (ví dụ: đã thanh toán)
    public void updateProject(ProjectEnity project) {
        projectRepository.save(project);
    }

    // Xóa dự án (nếu cần)
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
