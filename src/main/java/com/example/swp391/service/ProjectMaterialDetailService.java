package com.example.swp391.service;

import com.example.swp391.entity.MaterialEntity;
import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.entity.ProjectMaterialDetailEntity;
import com.example.swp391.repository.MaterialRepository;
import com.example.swp391.repository.ProjectMaterialDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectMaterialDetailService {

    @Autowired
    public ProjectMaterialDetailRepository projectMaterialDetailRepository;

    @Autowired
    private MaterialRepository materialRepository;

    // Tìm kiếm chi tiết vật liệu theo ID
    public Optional<ProjectMaterialDetailEntity> findById(Long materialDetailId) {
        return projectMaterialDetailRepository.findById(materialDetailId);
    }

    // Tìm chi tiết vật liệu theo projectId
    public List<ProjectMaterialDetailEntity> findByProjectId(Long projectId) {
        return projectMaterialDetailRepository.findByProject_ProjectId(projectId);
    }

    // Thêm vật liệu vào dự án
    @Transactional
    public void addMaterialToProject(ProjectEntity project, MaterialEntity material, int quantity) {
        if (project != null && material != null && quantity > 0) {
            ProjectMaterialDetailEntity detail = new ProjectMaterialDetailEntity();
            detail.setProject(project);
            detail.setMaterial(material);
            detail.setQuantity(quantity);
            projectMaterialDetailRepository.save(detail);
        }
    }

    // Xóa vật liệu khỏi dự án
    @Transactional
    public void removeMaterialFromProject(Long materialDetailId) {
        if (projectMaterialDetailRepository.existsById(materialDetailId)) {
            projectMaterialDetailRepository.deleteById(materialDetailId);
        }
    }

    // Cập nhật số lượng vật liệu
    @Transactional
    public void updateMaterialQuantity(Long materialDetailId, int quantity) {
        Optional<ProjectMaterialDetailEntity> detailOpt = projectMaterialDetailRepository.findById(materialDetailId);
        if (detailOpt.isPresent() && quantity > 0) {
            ProjectMaterialDetailEntity detail = detailOpt.get();
            detail.setQuantity(quantity);
            projectMaterialDetailRepository.save(detail);
        }
    }
}
