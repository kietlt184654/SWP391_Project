//package com.example.swp391.controller;
//
//import com.example.swp391.entity.MaterialEntity;
//import com.example.swp391.entity.ProjectEntity;
//import com.example.swp391.entity.ProjectMaterialDetailEntity;
//import com.example.swp391.service.MaterialService;
//import com.example.swp391.service.ProjectMaterialDetailService;
//import com.example.swp391.service.ProjectService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/materials")
//public class MaterialController {
//
//    @Autowired
//    private MaterialService materialService;
//
//    @Autowired
//    private ProjectMaterialDetailService projectMaterialDetailService;
//
//    @Autowired
//    private ProjectService projectService;
//
//    // Hiển thị tất cả các vật liệu
//    @GetMapping("/all")
//    public String showAllMaterials(Model model) {
//        List<MaterialEntity> materials = materialService.getAllMaterials();
//        model.addAttribute("materials", materials);
//        return "materialList";  // Trả về view hiển thị danh sách vật liệu
//    }
//
//    // Hiển thị thông tin chi tiết của vật liệu
//    @GetMapping("/{id}")
//    public String getMaterialById(@PathVariable("id") Long materialId, Model model) {
//        Optional<MaterialEntity> materialOpt = materialService.findById(materialId);
//        if (materialOpt.isPresent()) {
//            model.addAttribute("material", materialOpt.get());
//            return "materialDetail";  // Trả về view hiển thị chi tiết vật liệu
//        } else {
//            model.addAttribute("message", "Không tìm thấy vật liệu với ID: " + materialId);
//            return "error";
//        }
//    }
//
//    // Thêm vật liệu vào dự án
//    @PostMapping("/add")
//    public String addMaterialToProject(@RequestParam("projectId") Long projectId,
//                                       @RequestParam("materialId") Long materialId,
//                                       @RequestParam("quantity") int quantity, Model model) {
//        Optional<ProjectEntity> projectOpt = projectService.findProjectById(projectId);
//        Optional<MaterialEntity> materialOpt = materialService.findById(materialId);
//
//        if (projectOpt.isPresent() && materialOpt.isPresent()) {
//            ProjectEntity project = projectOpt.get();
//            MaterialEntity material = materialOpt.get();
//
//            // Kiểm tra số lượng tồn kho trước khi thêm vào dự án
//            if (material.getStockQuantity() < quantity) {
//                model.addAttribute("message", "Số lượng vật liệu không đủ trong kho.");
//                return "error";
//            }
//
//            // Thêm vật liệu vào dự án
//            projectMaterialDetailService.addMaterialToProject(project, material, quantity);
//
//            // Cập nhật số lượng tồn kho
//            material.setStockQuantity(material.getStockQuantity() - quantity);
//            materialService.saveMaterial(material);
//
//            return "redirect:/projects/" + projectId; // Chuyển hướng về trang chi tiết dự án
//        } else {
//            model.addAttribute("message", "Không tìm thấy vật liệu hoặc dự án.");
//            return "error";
//        }
//    }
//
//    // Cập nhật số lượng vật liệu trong dự án
//    @PostMapping("/update")
//    public String updateMaterialQuantity(@RequestParam("materialDetailId") Long materialDetailId,
//                                         @RequestParam("quantity") int quantity, Model model) {
//        Optional<ProjectMaterialDetailEntity> materialDetailOpt = projectMaterialDetailService.findById(materialDetailId);
//
//        if (materialDetailOpt.isPresent()) {
//            ProjectMaterialDetailEntity materialDetail = materialDetailOpt.get();
//            MaterialEntity material = materialDetail.getMaterial();
//            int currentQuantity = materialDetail.getQuantity();
//
//            // Kiểm tra số lượng tồn kho khi cập nhật
//            if (quantity > currentQuantity && (material.getStockQuantity() < (quantity - currentQuantity))) {
//                model.addAttribute("message", "Số lượng vật liệu không đủ trong kho.");
//                return "error";
//            }
//
//            // Cập nhật số lượng vật liệu trong dự án
//            projectMaterialDetailService.updateMaterialQuantity(materialDetailId, quantity);
//
//            // Điều chỉnh tồn kho
//            material.setStockQuantity(material.getStockQuantity() - (quantity - currentQuantity));
//            materialService.saveMaterial(material);
//
//            return "redirect:/materials/all";  // Trả về danh sách vật liệu sau khi cập nhật số lượng
//        } else {
//            model.addAttribute("message", "Không tìm thấy chi tiết vật liệu.");
//            return "error";
//        }
//    }
//
//    // Xóa vật liệu khỏi dự án
//    @PostMapping("/delete")
//    public String removeMaterialFromProject(@RequestParam("materialDetailId") Long materialDetailId, Model model) {
//        Optional<ProjectMaterialDetailEntity> materialDetailOpt = projectMaterialDetailService.findById(materialDetailId);
//
//        if (materialDetailOpt.isPresent()) {
//            ProjectMaterialDetailEntity materialDetail = materialDetailOpt.get();
//            MaterialEntity material = materialDetail.getMaterial();
//            int currentQuantity = materialDetail.getQuantity();
//
//            // Hoàn lại số lượng vật liệu về kho khi xóa khỏi dự án
//            material.setStockQuantity(material.getStockQuantity() + currentQuantity);
//            materialService.saveMaterial(material);
//
//            // Xóa vật liệu khỏi dự án
//            projectMaterialDetailService.removeMaterialFromProject(materialDetailId);
//
//            return "redirect:/materials/all";  // Trả về danh sách vật liệu sau khi xóa
//        } else {
//            model.addAttribute("message", "Không tìm thấy chi tiết vật liệu.");
//            return "error";
//        }
//    }
//}
