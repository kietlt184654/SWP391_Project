package com.example.swp391.controller;

import com.example.swp391.entity.*;
import com.example.swp391.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffProjectService staffProjectService;
    @Autowired
    private AccountService accountService;
@Autowired
private DesignService designService;

    @GetMapping("/showProjects")
    public String showProjects(Model model) {
        List<ProjectEntity> projects = projectService.findAll();  // Giả sử có phương thức lấy tất cả các project
        model.addAttribute("projects", projects);
        return "show_projects";
    }

    @GetMapping("/viewDetailProject/{projectID}")
    public String showDetailProject(@PathVariable Long projectID, Model model) {
        ProjectEntity project = projectService.getProjectById(projectID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + projectID));

        List<StaffProjectEntity> tasks = staffProjectService.getTasksByProjectId(projectID); // Lấy danh sách tasks
        model.addAttribute("project", project);
        model.addAttribute("projectId", projectID); // Thêm projectId vào model
        model.addAttribute("tasks", tasks); // Thêm danh sách tasks vào model

        return "viewDetailProject"; // Tên của file HTML cho trang chi tiết dự án
    }






    @PostMapping("/updateProjectStatus")
    public String updateProjectStatus(
            @RequestParam("projectId") Long projectId,
            @RequestParam("status") String status,
            RedirectAttributes redirectAttributes) {

        try {
            projectService.updateProjectStatus(projectId, status);
            redirectAttributes.addFlashAttribute("successMessage", "Project status updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update project status.");
        }

        // Corrected redirect URL
        return "redirect:/projects/viewDetailProject/" + projectId;
    }

    @PostMapping("/cancel")
    public String cancelProject(@RequestParam("projectId") Long projectId, Model model) {
        System.out.println("Cancel request received for Project ID: " + projectId);
        try {
            projectService.cancelProject(projectId);
            model.addAttribute("message", "Project canceled successfully.");
        } catch (Exception e) {
            e.printStackTrace(); // Log error details
            model.addAttribute("error", "Failed to cancel the project: " + e.getMessage());
        }
        return "redirect:/account/customer/completed-projects"; // Redirect to the projects list page
    }

        }
