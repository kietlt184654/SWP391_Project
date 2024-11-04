package com.example.swp391.controller;

import com.example.swp391.entity.*;
import com.example.swp391.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private DesignService designService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private DesignMaterialQuantityService designMaterialQuantityService;

    // Hiển thị danh sách các thiết kế chờ duyệt
    @GetMapping("/designs/pending")
    public String getPendingDesigns(Model model) {
        List<DesignEntity> pendingDesigns = designService.findByStatusAndTypeDesignId(DesignEntity.Status.Pending, 2);
        model.addAttribute("pendingDesigns", pendingDesigns);
        return "approvalDesigns"; // Trả về trang để hiển thị các thiết kế chờ duyệt
    }

    // Xem chi tiết thiết kế và thêm nguyên liệu
    @GetMapping("/designs/{designId}/details")
    public String viewDesignDetails(@PathVariable Long designId, Model model) {
        DesignEntity design = designService.findById(designId);
        if (design == null || design.getStatus() != DesignEntity.Status.Pending) {
            model.addAttribute("errorMessage", "Thiết kế không tồn tại hoặc không ở trạng thái Pending.");
            return "errorPage";
        }

        List<MaterialEntity> materials = materialService.findAllMaterials();
        model.addAttribute("design", design);
        model.addAttribute("materials", materials);
        return "addMaterials"; // Trả về trang để thêm nguyên liệu
    }

    // Xử lý thêm nguyên liệu cho thiết kế
    @PostMapping("/designs/{designId}/approve")
    public String addMaterialsToDesign(@PathVariable Long designId,
                                       @RequestParam Map<String, String> materials,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        DesignEntity design = designService.findById(designId);
        if (design != null && design.getStatus() == DesignEntity.Status.Pending) {
            List<DesignMaterialQuantity> materialQuantities = new ArrayList<>();
            try {
                // Duyệt qua các nguyên liệu và số lượng từ form
                for (Map.Entry<String, String> entry : materials.entrySet()) {
                    if (entry.getKey().startsWith("materials")) {
                        String[] keys = entry.getKey().split("\\.");
                        int index = Integer.parseInt(keys[0].replaceAll("[^0-9]", ""));
                        String field = keys[1];

                        if ("materialId".equals(field)) {
                            Long materialId = Long.parseLong(entry.getValue());
                            MaterialEntity material = materialService.findById(materialId);
                            if (material == null) {
                                redirectAttributes.addFlashAttribute("errorMessage", "Nguyên liệu không tồn tại: ID " + materialId);
                                return "redirect:/manager/designs/" + designId + "/details";
                            }

                            int quantityNeeded = Integer.parseInt(materials.get("materials[" + index + "].quantityNeeded"));
                            if (material.getStockQuantity() < quantityNeeded) {
                                redirectAttributes.addFlashAttribute("errorMessage", "Nguyên liệu " + material.getMaterialName() + " không đủ số lượng trong kho.");
                                return "redirect:/manager/designs/" + designId + "/details";
                            }

                            // Tạo đối tượng DesignMaterialQuantity và thêm vào danh sách
                            DesignMaterialQuantity dmq = new DesignMaterialQuantity();
                            dmq.setMaterial(material);
                            dmq.setQuantityNeeded(quantityNeeded);
                            dmq.setDesign(design);
                            materialQuantities.add(dmq);
                        }
                    }
                }

                // Lưu các DesignMaterialQuantity vào cơ sở dữ liệu
                for (DesignMaterialQuantity designMaterialQuantity : materialQuantities) {
                    designMaterialQuantityService.save(designMaterialQuantity);
                }

                // Cập nhật trạng thái thiết kế thành "NeedToPayment"
                design.setStatus(DesignEntity.Status.NeedToPayment);
                designService.save(design);

                // Tạo liên kết để khách hàng xem dự án sau khi thanh toán
                String customerProjectUrl = "/customer/project/" + design.getCustomerReference() + "/" + designId;
                model.addAttribute("customerProjectUrl", customerProjectUrl);
                redirectAttributes.addFlashAttribute("successMessage", "Thiết kế đã được duyệt và nguyên liệu đã được thêm.");
                return "redirect:/manager/designs/pending";
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Đã xảy ra lỗi khi thêm nguyên liệu: " + e.getMessage());
                return "addMaterials";
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Thiết kế không tồn tại hoặc không ở trạng thái Pending.");
            return "redirect:/manager/designs/pending";
        }
    }

    // Từ chối thiết kế
    @PostMapping("/designs/{designId}/reject")
    public String rejectDesign(@PathVariable Long designId, RedirectAttributes redirectAttributes) {
        DesignEntity design = designService.findById(designId);
        if (design != null && design.getStatus() == DesignEntity.Status.Pending) {
            design.setStatus(DesignEntity.Status.Rejected);
            designService.save(design);
            redirectAttributes.addFlashAttribute("message", "Thiết kế đã bị từ chối.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Thiết kế không tồn tại hoặc không ở trạng thái Pending.");
        }
        return "redirect:/manager/designs/pending";
    }

}
