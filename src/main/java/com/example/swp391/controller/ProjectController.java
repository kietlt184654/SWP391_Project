package com.example.swp391.controller;

import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.entity.StaffEntity;
import com.example.swp391.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/showProjects")
    public String showProjects(Model model) {
        List<ProjectEntity> projects = projectService.findAll();  // Giả sử có phương thức lấy tất cả các project
        model.addAttribute("projects", projects);
        return "show_projects";
    }

//    @GetMapping("/{projectId}/staffs")
//    public List<StaffEntity> getAvailableStaffs(@PathVariable Integer projectId) {
//        return projectService.getAvailableStaffs(projectId);
//    }
//
//    @PostMapping("/{projectId}/addStaff")
//    public ResponseEntity<?> addStaffToProject(@PathVariable Integer projectId, @RequestBody Integer staffId) {
//        projectService.addStaffToProject(projectId, staffId);
//        return ResponseEntity.ok("Staff added to project successfully");
//    }
}
