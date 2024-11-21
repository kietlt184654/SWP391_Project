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
import java.util.stream.Collectors;

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

        List<StaffProjectEntity> tasks = staffProjectService.getTasksByProjectId(projectID);
        model.addAttribute("project", project);
        model.addAttribute("projectId", projectID); // Make sure projectId is added to the model
        model.addAttribute("tasks", tasks);

        return "viewDetailProject";
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
    @PostMapping("/delete")
    public String deleteProject(@RequestParam("projectId") Long projectId, RedirectAttributes redirectAttributes) {
        try {
            projectService.deleteProject(projectId);
            redirectAttributes.addFlashAttribute("message", "Project deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete the project: " + e.getMessage());
        }
        return "redirect:/projects/showProjects"; // Redirect to the project list page
    }
    @GetMapping("/{projectId}/progress")
    public String viewProjectProgress(@PathVariable Long projectId, Model model) {
        // Lấy danh sách StaffProjectEntity từ projectId
        List<StaffProjectEntity> tasks = staffProjectService.getProgressImagesByProjectId(projectId);

        // Lấy danh sách ảnh
        List<String> progressImages = tasks.stream()
                .map(StaffProjectEntity::getProgressImage)
                .filter(image -> image != null && !image.isEmpty()) // Bỏ qua null/chuỗi rỗng
                .map(image -> {
                    if (image.startsWith("http://") || image.startsWith("https://")) {
                        // Ảnh từ URL bên ngoài
                        return image;
                    } else if (image.startsWith("/uploads/")) {
                        // Tránh thêm lại "/uploads/" nếu đã tồn tại
                        return image;
                    } else {
                        // Ảnh cục bộ, thêm "/uploads/" vào đầu
                        return "/uploads/" + image;
                    }
                })

                .collect(Collectors.toList());

        // Log để kiểm tra đường dẫn ảnh
        progressImages.forEach(System.out::println);

        // Thêm dữ liệu vào model
        model.addAttribute("projectId", projectId);
        model.addAttribute("progressImages", progressImages);
        model.addAttribute("tasks", tasks);

        return "viewProjectProgress";
    }



}
