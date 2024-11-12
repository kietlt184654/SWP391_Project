package com.example.swp391.service;

import com.example.swp391.entity.ProjectMaterialDetailEntity;
import com.example.swp391.repository.ProjectMaterialDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProjectMaterialDetailService {

    @Autowired
    private ProjectMaterialDetailRepository projectMaterialDetailRepository;

    public List<ProjectMaterialDetailEntity> findAllMaterialDetails() {
        return projectMaterialDetailRepository.findAll();
    }

    public Map<Long, Integer> calculateTotalUsedQuantities() {
        List<ProjectMaterialDetailEntity> details = findAllMaterialDetails();
        return details.stream()
                .collect(Collectors.groupingBy(
                        detail -> detail.getMaterial().getMaterialId(),
                        Collectors.summingInt(ProjectMaterialDetailEntity::getQuantityUsed)
                ));
    }

    public ProjectMaterialDetailEntity saveMaterialDetail(ProjectMaterialDetailEntity detail) {
        return projectMaterialDetailRepository.save(detail);
    }
}