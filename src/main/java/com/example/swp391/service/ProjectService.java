package com.example.swp391.service;

import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.entity.StaffEntity;
import com.example.swp391.entity.StaffProjectEntity;
import com.example.swp391.entity.StaffProjectId;
import com.example.swp391.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

//    public List<StaffEntity> getAvailableStaffs(Integer projectId) {
//        // Lấy tất cả nhân viên không tham gia dự án này
//        return staffRepository.findAvailableStaffs(projectId);
//    }
//
//    public void addStaffToProject(Integer projectId, Integer staffId) {
//        StaffProjectId staffProjectId = new StaffProjectId(staffId, projectId);
//        StaffProjectEntity staffProjectEntity = new StaffProjectEntity(staffProjectId, new Date(), "");
//        staffProjectRepository.save(staffProjectEntity);
//    }
}
