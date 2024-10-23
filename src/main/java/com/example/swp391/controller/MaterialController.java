package com.example.swp391.controller;

import com.example.swp391.entity.MaterialEntity;

import com.example.swp391.entity.ProjectMaterialDetailEntity;
import com.example.swp391.service.MaterialService;
import com.example.swp391.service.ProjectMaterialDetailService;
import com.example.swp391.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ProjectMaterialDetailService projectMaterialDetailService;

    @Autowired
    private ProjectService projectService;

    /**
     * Hiển thị danh sách tất cả các nguyên vật liệu trong kho
     */
    @GetMapping("/list")
    public String listMaterials(Model model) {
        List<MaterialEntity> materials = materialService.getAllMaterials();
        model.addAttribute("materials", materials);
        return "materialList";  // Trả về giao diện danh sách nguyên vật liệu
    }

    /**
     * Xem chi tiết nguyên vật liệu
     */
    @GetMapping("/detail/{materialId}")
    public String viewMaterialDetail(@PathVariable Long materialId, Model model) {
        MaterialEntity material = materialService.getMaterialById(materialId);
        model.addAttribute("material", material);
        return "materialDetail";  // Trả về giao diện chi tiết nguyên vật liệu
    }

    /**
     * Xóa nguyên vật liệu khỏi kho
     */
    @PostMapping("/delete/{materialId}")
    public String deleteMaterial(@PathVariable Long materialId, Model model) {
        try {
            materialService.deleteMaterialById(materialId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";  // Trả về trang lỗi nếu có vấn đề
        }
        return "redirect:/material/list";  // Quay lại danh sách sau khi xóa
    }

    /**
     * Xem chi tiết nguyên vật liệu được sử dụng trong từng dự án
     */
    @GetMapping("/projectDetails/{materialId}")
    public String viewMaterialProjectDetails(@PathVariable Long materialId, Model model) {
        List<ProjectMaterialDetailEntity> projectDetails = projectMaterialDetailService.getProjectDetailsForMaterial(materialId);
        model.addAttribute("projectDetails", projectDetails);
        return "materialProjectDetails";  // Trả về giao diện chi tiết nguyên vật liệu trong các dự án
    }
    // Hiển thị form thay đổi số lượng nguyên vật liệu
    @GetMapping("/updateQuantity/{materialId}")
    public String showUpdateQuantityForm(@PathVariable Long materialId, Model model) {
        MaterialEntity material = materialService.getMaterialById(materialId);
        model.addAttribute("material", material);
        return "updateMaterialQuantity";  // Giao diện để nhập số lượng thay đổi và lý do
    }

    // Xử lý cập nhật số lượng nguyên vật liệu
    @PostMapping("/updateQuantity")
    public String updateMaterialQuantity(@RequestParam("materialId") Long materialId,
                                         @RequestParam("quantityChange") int quantityChange,
                                         @RequestParam("reason") String reason,
                                         Model model) {
        try {
            materialService.updateMaterialQuantity(materialId, quantityChange, reason);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";  // Trả về trang lỗi nếu có vấn đề
        }
        return "redirect:/material/list";  // Quay lại danh sách sau khi cập nhật
    }
}