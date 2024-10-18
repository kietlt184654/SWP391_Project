package com.example.swp391.controller;

import com.example.swp391.entity.ProjectMaterialDetailEntity;
import com.example.swp391.repository.ProjectMaterialDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/project-materials")
public class ProjectMaterialDetailController {

    @Autowired
    private ProjectMaterialDetailRepository projectMaterialDetailRepository;

    // Lấy danh sách vật liệu sử dụng trong một dự án
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectMaterialDetailEntity>> getMaterialsByProjectId(@PathVariable Long projectId) {
        List<ProjectMaterialDetailEntity> materials = projectMaterialDetailRepository.findByProject_ProjectId(projectId);
        return ResponseEntity.ok(materials);
    }
}

