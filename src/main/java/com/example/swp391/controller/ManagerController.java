//package com.example.swp391.controller;
//
//import com.example.swp391.entity.*;
//import com.example.swp391.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/manager")
//public class ManagerController {
//
//    @Autowired
//    private DesignService designService;
//    @Autowired
//    private MaterialService materialService;
//    @Autowired
//    private DesignMaterialQuantityService designMaterialQuantityService;
//
//
//    @GetMapping("")
//    public String showManagerPage() {
//        return "manager";  // Tên file HTML là manager.html trong thư mục templates
//    }
//    // Display pending designs list
//    @GetMapping("/designs/pending")
//    public String getPendingDesigns(Model model) {
//        List<DesignEntity> pendingDesigns = designService.findByStatusAndTypeDesignId(DesignEntity.Status.Pending, 2);
//        model.addAttribute("pendingDesigns", pendingDesigns);
//        return "approvalDesigns";
//    }
//
//    // View design details and add materials
//    @GetMapping("/designs/{designId}/details")
//    public String viewDesignDetails(@PathVariable Long designId, Model model) {
//        DesignEntity design = designService.findById(designId);
//        if (design == null || design.getStatus() != DesignEntity.Status.Pending) {
//            model.addAttribute("errorMessage", "The design does not exist or is not in Pending status.");
//            return "errorPage";
//        }
//
//        List<MaterialEntity> materials = materialService.findAllMaterials();
//        model.addAttribute("design", design);
//        model.addAttribute("materials", materials);
//        return "addMaterials";
//    }
//
//    // Handle adding materials to design
//    @PostMapping("/designs/{designId}/approve")
//    public String addMaterialsToDesign(@PathVariable Long designId,
//                                       @RequestParam Map<String, String> materials,
//                                       RedirectAttributes redirectAttributes,
//                                       Model model) {
//        DesignEntity design = designService.findById(designId);
//        if (design != null && design.getStatus() == DesignEntity.Status.Pending) {
//            List<DesignMaterialQuantity> materialQuantities = new ArrayList<>();
//            try {
//                for (Map.Entry<String, String> entry : materials.entrySet()) {
//                    if (entry.getKey().startsWith("materials")) {
//                        String[] keys = entry.getKey().split("\\.");
//                        int index = Integer.parseInt(keys[0].replaceAll("[^0-9]", ""));
//                        String field = keys[1];
//
//                        if ("materialId".equals(field)) {
//                            Long materialId = Long.parseLong(entry.getValue());
//                            MaterialEntity material = materialService.findById(materialId);
//                            if (material == null) {
//                                redirectAttributes.addFlashAttribute("errorMessage", "Material does not exist: ID " + materialId);
//                                return "redirect:/manager/designs/" + designId + "/details";
//                            }
//
//                            int quantityNeeded = Integer.parseInt(materials.get("materials[" + index + "].quantityNeeded"));
//                            if (material.getStockQuantity() < quantityNeeded) {
//                                redirectAttributes.addFlashAttribute("errorMessage", "Material " + material.getMaterialName() + " does not have sufficient stock.");
//                                return "redirect:/manager/designs/" + designId + "/details";
//                            }
//
//                            DesignMaterialQuantity dmq = new DesignMaterialQuantity();
//                            dmq.setMaterial(material);
//                            dmq.setQuantityNeeded(quantityNeeded);
//                            dmq.setDesign(design);
//                            materialQuantities.add(dmq);
//                        }
//                    }
//                }
//
//                for (DesignMaterialQuantity designMaterialQuantity : materialQuantities) {
//                    designMaterialQuantityService.save(designMaterialQuantity);
//                }
//
//                design.setStatus(DesignEntity.Status.NeedToPayment);
//                designService.save(design);
//
//                String customerProjectUrl = "/customer/project/" + design.getCustomerReference() + "/" + designId;
//                model.addAttribute("customerProjectUrl", customerProjectUrl);
//                redirectAttributes.addFlashAttribute("successMessage", "The design has been approved, and materials have been added.");
//                return "redirect:/manager/designs/pending";
//            } catch (Exception e) {
//                model.addAttribute("errorMessage", "An error occurred while adding materials: " + e.getMessage());
//                return "addMaterials";
//            }
//        } else {
//            redirectAttributes.addFlashAttribute("errorMessage", "The design does not exist or is not in Pending status.");
//            return "redirect:/manager/designs/pending";
//        }
//    }
//
//    // Reject design
//    @PostMapping("/designs/{designId}/reject")
//    public String rejectDesign(@PathVariable Long designId, RedirectAttributes redirectAttributes) {
//        DesignEntity design = designService.findById(designId);
//        if (design != null && design.getStatus() == DesignEntity.Status.Pending) {
//            design.setStatus(DesignEntity.Status.Rejected);
//            designService.save(design);
//            redirectAttributes.addFlashAttribute("message", "The design has been rejected.");
//        } else {
//            redirectAttributes.addFlashAttribute("errorMessage", "The design does not exist or is not in Pending status.");
//        }
//        return "redirect:/manager/designs/pending";
//    }
//}

package com.example.swp391.controller;

import com.example.swp391.entity.DesignEntity;
import com.example.swp391.entity.MaterialEntity;
import com.example.swp391.entity.DesignMaterialQuantity;
import com.example.swp391.service.DesignService;
import com.example.swp391.service.MaterialService;
import com.example.swp391.service.DesignMaterialQuantityService;
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

    @GetMapping("")
    public String showManagerPage() {
        return "manager";  // Tên file HTML là manager.html trong thư mục templates
    }

    // Hiển thị danh sách các thiết kế ở trạng thái 'Designed' cần phê duyệt
    @GetMapping("/designs/pending")
    public String getPendingDesigns(Model model) {
        List<DesignEntity> pendingDesigns = designService.findByStatusAndTypeDesignId(DesignEntity.Status.Designed, 2);
        model.addAttribute("pendingDesigns", pendingDesigns);
        return "approvalDesigns";
    }

    // Xem chi tiết thiết kế và thêm vật liệu
    @GetMapping("/designs/{designId}/details")
    public String viewDesignDetails(@PathVariable Long designId, Model model) {
        DesignEntity design = designService.findById(designId);
        if (design == null || design.getStatus() != DesignEntity.Status.Designed) {
            model.addAttribute("errorMessage", "The design does not exist or is not in Designed status.");
            return "errorPage";
        }

        List<MaterialEntity> materials = materialService.findAllMaterials();
        model.addAttribute("design", design);
        model.addAttribute("materials", materials);
        return "addMaterials";
    }

    // Xử lý việc thêm vật liệu vào thiết kế và phê duyệt thiết kế
    @PostMapping("/designs/{designId}/approve")
    public String addMaterialsToDesign(@PathVariable Long designId,
                                       @RequestParam Map<String, String> materials,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        DesignEntity design = designService.findById(designId);
        if (design != null && design.getStatus() == DesignEntity.Status.Designed) {
            List<DesignMaterialQuantity> materialQuantities = new ArrayList<>();
            try {
                for (Map.Entry<String, String> entry : materials.entrySet()) {
                    if (entry.getKey().startsWith("materials")) {
                        String[] keys = entry.getKey().split("\\.");
                        int index = Integer.parseInt(keys[0].replaceAll("[^0-9]", ""));
                        String field = keys[1];

                        if ("materialId".equals(field)) {
                            Long materialId = Long.parseLong(entry.getValue());
                            MaterialEntity material = materialService.findById(materialId);
                            if (material == null) {
                                redirectAttributes.addFlashAttribute("errorMessage", "Material does not exist: ID " + materialId);
                                return "redirect:/manager/designs/" + designId + "/details";
                            }

                            int quantityNeeded = Integer.parseInt(materials.get("materials[" + index + "].quantityNeeded"));
                            if (material.getStockQuantity() < quantityNeeded) {
                                redirectAttributes.addFlashAttribute("errorMessage", "Material " + material.getMaterialName() + " does not have sufficient stock.");
                                return "redirect:/manager/designs/" + designId + "/details";
                            }

                            DesignMaterialQuantity dmq = new DesignMaterialQuantity();
                            dmq.setMaterial(material);
                            dmq.setQuantityNeeded(quantityNeeded);
                            dmq.setDesign(design);
                            materialQuantities.add(dmq);
                        }
                    }
                }

                // Lưu tất cả vật liệu vào thiết kế
                for (DesignMaterialQuantity designMaterialQuantity : materialQuantities) {
                    designMaterialQuantityService.save(designMaterialQuantity);
                }

                // Cập nhật trạng thái thiết kế sang NeedToPayment
                design.setStatus(DesignEntity.Status.NeedToPayment);
                designService.save(design);

                String customerProjectUrl = "/customer/project/" + design.getCustomerReference() + "/" + designId;
                model.addAttribute("customerProjectUrl", customerProjectUrl);
                redirectAttributes.addFlashAttribute("successMessage", "The design has been approved, and materials have been added.");
                return "redirect:/manager/designs/pending";
            } catch (Exception e) {
                model.addAttribute("errorMessage", "An error occurred while adding materials: " + e.getMessage());
                return "addMaterials";
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "The design does not exist or is not in Designed status.");
            return "redirect:/manager/designs/pending";
        }
    }

    // Từ chối thiết kế
    @PostMapping("/designs/{designId}/reject")
    public String rejectDesign(@PathVariable Long designId, RedirectAttributes redirectAttributes) {
        DesignEntity design = designService.findById(designId);
        if (design != null && design.getStatus() == DesignEntity.Status.Designed) {
            design.setStatus(DesignEntity.Status.Rejected); // Từ chối thiết kế với trạng thái Rejected
            designService.save(design);
            redirectAttributes.addFlashAttribute("message", "The design has been rejected.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "The design does not exist or is not in Designed status.");
        }
        return "redirect:/manager/designs/pending";
    }
}