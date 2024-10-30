package com.example.swp391.controller;

import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.entity.StaffEntity;
import com.example.swp391.entity.StaffProjectEntity;
import com.example.swp391.service.ProjectService;
import com.example.swp391.service.StaffProjectService;
import com.example.swp391.service.StaffService;
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
    @Autowired
    private StaffService staffService;


    @GetMapping("/showProjects")
    public String showProjects(Model model) {
        List<ProjectEntity> projects = projectService.findAll();  // Giả sử có phương thức lấy tất cả các project
        model.addAttribute("projects", projects);
        return "show_projects";
    }

    @GetMapping("/viewDetailProject/{projectID}")
    public String showDetailProject(@PathVariable Integer projectID, Model model) {
        ProjectEntity project = projectService.getProjectById(projectID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + projectID));
        model.addAttribute("project", project);
        model.addAttribute("projectId", projectID); // Thêm projectId vào model để sử dụng trong view
        return "viewDetailProject"; // Tên của file HTML cho trang chi tiết dự án
    }









}
