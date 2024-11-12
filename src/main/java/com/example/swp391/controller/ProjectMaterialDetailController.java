package com.example.swp391.controller;

import com.example.swp391.entity.ProjectMaterialDetailEntity;
import com.example.swp391.service.ProjectMaterialDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/materials")
public class ProjectMaterialDetailController {

    @Autowired
    private ProjectMaterialDetailService projectMaterialDetailService;

    @GetMapping("/history")
    public String showMaterialHistory(Model model) {
        List<ProjectMaterialDetailEntity> materialsUsed = projectMaterialDetailService.findAllMaterialDetails();
        Map<Long, Integer> totalUsedQuantities = projectMaterialDetailService.calculateTotalUsedQuantities();

        model.addAttribute("materialsUsed", materialsUsed);
        model.addAttribute("totalUsedQuantities", totalUsedQuantities);
        return "material-history";
    }

    @PostMapping("/history/add")
    public String addMaterialDetail(ProjectMaterialDetailEntity detail, RedirectAttributes redirectAttributes) {
        projectMaterialDetailService.saveMaterialDetail(detail);
        redirectAttributes.addFlashAttribute("message", "Material usage details have been successfully added.");
        return "redirect:/materials/history";
    }
}