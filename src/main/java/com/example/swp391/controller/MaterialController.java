package com.example.swp391.controller;

import com.example.swp391.entity.MaterialChangeLogEntity;
import com.example.swp391.entity.MaterialEntity;
import com.example.swp391.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/management")
    public String showMaterialManagementPage(Model model) {
        return "material-management";
    }

    @GetMapping("/list")
    public String showMaterialList(Model model) {
        List<MaterialEntity> materials = materialService.findAllMaterials();
        model.addAttribute("materials", materials);
        return "material-list";
    }

    @PostMapping("/{materialId}/update-quantity")
    public String updateMaterialQuantity(
            @PathVariable Long materialId,
            @RequestParam int quantityChange,
            @RequestParam String reason,
            RedirectAttributes redirectAttributes) {

        if (quantityChange == 0){
            redirectAttributes.addFlashAttribute("message", "Quantity change cannot be zero.");
        }
        try {
            materialService.updateMaterialQuantity(materialId, quantityChange, reason);
            redirectAttributes.addFlashAttribute("message", "Material quantity has been successfully updated.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }

        return "redirect:/materials/list";
    }

    @PostMapping("/{materialId}/delete")
    public String deleteMaterial(@PathVariable Long materialId) {
        materialService.deleteMaterialById(materialId);
        return "redirect:/materials/list";
    }

    @PostMapping("/add")
    public String addMaterial(
            @RequestParam("materialName") String materialName,
            @RequestParam("stockQuantity") int stockQuantity,
            @RequestParam("unit") String unit,
            Model model) {

        if (materialService.existsByMaterialName(materialName)) {
            model.addAttribute("error", "Material name already exists.");
            return "redirect:/materials/add";
        }
        if (stockQuantity <= 0) {
            model.addAttribute("error", "Stock quantity must be greater than 0.");
            return "redirect:/materials/add";
        }

        MaterialEntity material = new MaterialEntity();
        material.setMaterialName(materialName);
        material.setStockQuantity(stockQuantity);
        material.setUnit(unit);

        materialService.save(material);
        model.addAttribute("message", "Material has been successfully added!");
        return "redirect:/materials/list";
    }

    @GetMapping("/historyofchanging")
    public String showAllMaterialHistory(Model model) {
        List<MaterialChangeLogEntity> changeLogs = materialService.getAllMaterialChangeHistory();
        model.addAttribute("changeLogs", changeLogs);
        return "material-historychanging";
    }
}
