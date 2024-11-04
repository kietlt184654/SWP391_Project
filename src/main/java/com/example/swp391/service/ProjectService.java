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


    public List<ProjectEntity> findAll() {
        return projectRepository.findAll();
    }

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StaffProjectRepository staffProjectRepository;




    public Optional<ProjectEntity> getProjectById(Integer projectId) {
        return projectRepository.findById(projectId);
    }
}
