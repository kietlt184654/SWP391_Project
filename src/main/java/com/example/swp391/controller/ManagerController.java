//package com.example.swp391.controller;
//
//import com.example.swp391.entity.*;
//import com.example.swp391.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class ManagerController {
//
//    @Autowired
//    private DesignService designService;
//    @Autowired
//    private MaterialService materialService;
//    @Autowired
//    private DesignMaterialQuantityService designMaterialQuantityService;
//
//    @GetMapping("/manager/design/approval")
//    public String getPendingDesignsForApproval(Model model) {
//        // Lấy danh sách thiết kế có trạng thái "Pending" và có typeDesignId = 2 (Thiết kế riêng)
//        List<DesignEntity> pendingDesigns = designService.findByStatusAndTypeDesignId(DesignEntity.Status.Pending, 2);
//        model.addAttribute("pendingDesigns", pendingDesigns);
//        return "approvalDesigns"; // Trả về trang HTML để hiển thị danh sách các thiết kế chờ duyệt
//    }
//
//    @GetMapping("/manager/design/approve/{id}")
//    public String approveDesign(@PathVariable Long id, Model model) {
//        // Tìm thiết kế từ DB
//        DesignEntity design = designService.findById(id);
//        if (design != null && design.getStatus() == DesignEntity.Status.Pending) {
//            // Lấy danh sách nguyên liệu từ DB thông qua MaterialService
//            List<MaterialEntity> materials = materialService.findAllMaterials();
//
//            // Thêm danh sách nguyên liệu và thiết kế vào model để truyền sang view
//            model.addAttribute("materials", materials);
//            model.addAttribute("design", design);
//
//            // Chuyển hướng tới trang thêm nguyên liệu
//            return "addMaterials";
//        }
//        return "redirect:/manager/design/approval";
//    }
//
//    @PostMapping("/design/add-materials")
//    public String addMaterialsToDesign(@RequestParam("designId") Long designId,
//                                       @RequestParam Map<String, String> materials,
//                                       Model model) {
//        DesignEntity design = designService.findById(designId);
//        if (design != null && design.getStatus() == DesignEntity.Status.Pending) {
//            List<DesignMaterialQuantity> materialQuantities = new ArrayList<>();
//
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
//                                model.addAttribute("errorMessage", "Nguyên liệu không tồn tại: ID " + materialId);
//                                model.addAttribute("design", design);
//                                return "addMaterials";
//                            }
//
//                            int quantityNeeded = Integer.parseInt(materials.get("materials[" + index + "].quantityNeeded"));
//                            if (material.getStockQuantity() < quantityNeeded) {
//                                model.addAttribute("errorMessage", "Nguyên liệu " + material.getMaterialName() + " không đủ số lượng trong kho.");
//                                model.addAttribute("design", design);
//                                return "addMaterials";
//                            }
//
//                            DesignMaterialQuantity dmq = new DesignMaterialQuantity();
//                            dmq.setMaterial(material);
//                            dmq.setQuantityNeeded(quantityNeeded);
//                            dmq.setDesign(design); // Thiết lập liên kết ngược với DesignEntity
//                            materialQuantities.add(dmq);
//                        }
//                    }
//                }
//
//                // Thêm danh sách DesignMaterialQuantity vào DesignEntity
//                design.setDesignMaterialQuantities(materialQuantities);
//                // Cập nhật trạng thái thành "Need to payment" sau khi thêm thành công
//                design.setStatus(DesignEntity.Status.Available);
//
//                designService.save(design); // Lưu DesignEntity, sẽ tự động lưu các DesignMaterialQuantity nhờ CascadeType.ALL
//
//                model.addAttribute("successMessage", "Đã thêm nguyên liệu thành công và cập nhật trạng thái thiết kế thành Need to payment.");
//
//                // Tạo đường dẫn để customer có thể xem thiết kế
//                String customerDesignUrl = "/customer/design/" + design.getDesignId();
//                model.addAttribute("customerDesignUrl", customerDesignUrl);
//
//                return "materialAddedSuccess"; // Trả về trang thông báo thành công
//            } catch (Exception e) {
//                model.addAttribute("errorMessage", "Đã xảy ra lỗi khi thêm nguyên liệu: " + e.getMessage());
//                model.addAttribute("design", design);
//                return "addMaterials";
//            }
//        } else {
//            model.addAttribute("errorMessage", "Thiết kế không tồn tại hoặc không ở trạng thái Pending");
//        }
//        return "redirect:/manager/design/approval";
//    }
//
//}
