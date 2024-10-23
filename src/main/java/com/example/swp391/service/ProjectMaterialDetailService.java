package com.example.swp391.service;


import com.example.swp391.entity.ProjectMaterialDetailEntity;

import com.example.swp391.repository.ProjectMaterialDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class ProjectMaterialDetailService {

    @Autowired
    public ProjectMaterialDetailRepository projectMaterialDetailRepository;



    /**
     * Lấy chi tiết nguyên vật liệu được sử dụng trong từng dự án
     */
    public List<ProjectMaterialDetailEntity> getProjectDetailsForMaterial(Long materialId) {
        return projectMaterialDetailRepository.findByMaterial_MaterialId(materialId);
    }
}
