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
        return "material-management"; // Trả về view quản lý nguyên vật liệu
    }
    // Hiển thị danh sách nguyên vật liệu cùng form cập nhật
    @GetMapping("/list")
    public String showMaterialList(Model model) {
        List<MaterialEntity> materials = materialService.findAllMaterials();
        model.addAttribute("materials", materials);
        return "material-list"; // Trả về trang danh sách
    }

    // Xử lý cập nhật số lượng nguyên vật liệu
    @PostMapping("/{materialId}/update-quantity")
    public String updateMaterialQuantity(
            @PathVariable Long materialId,
            @RequestParam int quantityChange,
            @RequestParam String reason,
            RedirectAttributes redirectAttributes) { // Dùng RedirectAttributes để truyền thông báo
        try {
            materialService.updateMaterialQuantity(materialId, quantityChange, reason);
            // Thêm thông báo thành công vào redirect attributes
            redirectAttributes.addFlashAttribute("message", "Số lượng nguyên vật liệu đã được cập nhật thành công.");
        } catch (IllegalArgumentException e) {
            // Thêm thông báo lỗi vào redirect attributes
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }

        // Redirect đến trang danh sách sau khi cập nhật thành công
        return "redirect:/materials/list";
    }
//    @GetMapping("/{materialId}/historyofchanging")
//    public String showMaterialHistory(@PathVariable Long materialId, Model model) {
//        List<MaterialChangeLogEntity> changeLogs = materialService.getMaterialChangeHistory(materialId);
//        model.addAttribute("changeLogs", changeLogs);
//        return "material-historychanging"; // Trả về view hiển thị lịch sử chỉnh sửa
//    }
@GetMapping("/historyofchanging")
public String showAllMaterialHistory(Model model) {
        List<MaterialChangeLogEntity> changeLogs = materialService.getAllMaterialChangeHistory();
        model.addAttribute("changeLogs", changeLogs);
    return "material-historychanging"; // Trả về trang hiển thị toàn bộ lịch sử
}


}
