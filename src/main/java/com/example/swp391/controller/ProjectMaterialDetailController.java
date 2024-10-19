package com.example.swp391.controller;

import com.example.swp391.entity.ProjectMaterialDetailEntity;
import com.example.swp391.entity.MaterialEntity;
import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.service.MaterialService;
import com.example.swp391.service.ProjectMaterialDetailService;
import com.example.swp391.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/materials")
public class ProjectMaterialDetailController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ProjectMaterialDetailService projectMaterialDetailService;

    // Hiển thị danh sách vật liệu của một dự án
    @GetMapping("/project/{projectId}")
    public String showMaterialsForProject(@PathVariable("projectId") Long projectId, Model model) {
        // Tìm dự án theo ID, xử lý Optional
        Optional<ProjectEntity> projectOpt = projectService.findProjectById(projectId);
        if (!projectOpt.isPresent()) {
            model.addAttribute("message", "Dự án không tồn tại!");
            return "error";
        }

        ProjectEntity project = projectOpt.get();

        // Lấy danh sách vật liệu chi tiết cho dự án
        List<ProjectMaterialDetailEntity> materialDetails = projectMaterialDetailService.findByProjectId(projectId);

        model.addAttribute("materialDetails", materialDetails);
        model.addAttribute("project", project);

        return "materialDetailList";  // Trả về view để hiển thị danh sách chi tiết vật liệu
    }

    // Thêm một vật liệu mới vào dự án
    @PostMapping("/add")
    public String addMaterialToProject(@RequestParam("projectId") Long projectId,
                                       @RequestParam("materialId") Long materialId,
                                       @RequestParam("quantity") int quantity, Model model) {
        // Tìm dự án và vật liệu theo ID
        Optional<ProjectEntity> projectOpt = projectService.findProjectById(projectId);
        Optional<MaterialEntity> materialOpt = materialService.findById(materialId);

        if (!projectOpt.isPresent() || !materialOpt.isPresent()) {
            model.addAttribute("message", "Dự án hoặc vật liệu không tồn tại!");
            return "error";
        }

        ProjectEntity project = projectOpt.get();
        MaterialEntity material = materialOpt.get();

        // Thêm vật liệu vào dự án
        projectMaterialDetailService.addMaterialToProject(project, material, quantity);

        return "redirect:/materials/project/" + projectId;  // Sau khi thêm, chuyển hướng lại trang chi tiết vật liệu
    }

    // Xóa một vật liệu khỏi dự án
    @PostMapping("/remove")
    public String removeMaterialFromProject(@RequestParam("projectId") Long projectId,
                                            @RequestParam("materialDetailId") Long materialDetailId, Model model) {
        Optional<ProjectEntity> projectOpt = projectService.findProjectById(projectId);
        if (!projectOpt.isPresent()) {
            model.addAttribute("message", "Dự án không tồn tại!");
            return "error";
        }

        // Xóa vật liệu
        projectMaterialDetailService.removeMaterialFromProject(materialDetailId);

        return "redirect:/materials/project/" + projectId;  // Sau khi xóa, chuyển hướng lại trang chi tiết vật liệu
    }

    // Cập nhật số lượng của một vật liệu trong dự án
    @PostMapping("/update")
    public String updateMaterialQuantityInProject(@RequestParam("projectId") Long projectId,
                                                  @RequestParam("materialDetailId") Long materialDetailId,
                                                  @RequestParam("quantity") int quantity, Model model) {
        Optional<ProjectEntity> projectOpt = projectService.findProjectById(projectId);
        if (!projectOpt.isPresent()) {
            model.addAttribute("message", "Dự án không tồn tại!");
            return "error";
        }

        // Cập nhật số lượng vật liệu trong dự án
        projectMaterialDetailService.updateMaterialQuantity(materialDetailId, quantity);

        return "redirect:/materials/project/" + projectId;  // Sau khi cập nhật, chuyển hướng lại trang chi tiết vật liệu
    }

}
