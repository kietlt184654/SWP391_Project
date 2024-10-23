package com.example.swp391.service;

import com.example.swp391.entity.*;
import com.example.swp391.repository.ProjectMaterialDetailRepository;
import com.example.swp391.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMaterialDetailRepository projectMaterialDetailRepository;

    // Tìm kiếm dự án theo ID
    public Optional<ProjectEntity> findProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    // Lấy tất cả các dự án
    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }

    // Lưu dự án (thêm mới hoặc cập nhật)
    public ProjectEntity saveProject(ProjectEntity project) {
        return projectRepository.save(project);
    }

    // Cập nhật dự án
    public void updateProject(ProjectEntity project) {
        projectRepository.save(project);  // Cập nhật dự án
    }

    // Xóa dự án theo ID
    public void deleteProjectById(Long projectId) {
        if (projectRepository.existsById(projectId)) {
            projectRepository.deleteById(projectId);
        }
    }
    // Tạo chi tiết nguyên vật liệu cho dự án
    private void createProjectMaterialDetails(ProjectEntity project, DesignEntity design) {
        for (Map.Entry<MaterialEntity, Integer> entry : design.getMaterialQuantities().entrySet()) {
            MaterialEntity material = entry.getKey();
            int quantityUsed = entry.getValue();

            ProjectMaterialDetailEntity detail = new ProjectMaterialDetailEntity();
            detail.setProject(project);
            detail.setMaterial(material);
            detail.setQuantityUsed(quantityUsed);

            projectMaterialDetailRepository.save(detail);
        }
    }



    }


