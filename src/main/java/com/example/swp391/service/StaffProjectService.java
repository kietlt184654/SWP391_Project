package com.example.swp391.service;//package com.example.swp391.service;
//
//import com.example.swp391.entity.StaffProjectEntity;
//import com.example.swp391.repository.StaffProjectRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class StaffProjectService {
//
//    private final StaffProjectRepository staffProjectRepository;
//
//
//    @Autowired
//    public StaffProjectService(StaffProjectRepository staffProjectRepository) {
//        this.staffProjectRepository = staffProjectRepository;
//    }
//
//    public List<StaffProjectEntity> getTasksByProjectId(Long projectId) {
//        return staffProjectRepository.findByProjectProjectId(projectId);
//    }
//
//    public StaffProjectEntity assignTaskToStaff(StaffProjectEntity staffProjectEntity) {
//        return staffProjectRepository.save(staffProjectEntity);
//    }
//}
