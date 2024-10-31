package com.example.swp391.controller;

import com.example.swp391.dto.ServiceRequest;
import com.example.swp391.entity.DesignEntity;
import com.example.swp391.service.DesignService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/services")
public class MaintenanceController {
    @Autowired
    private DesignService designService;

    @GetMapping("/serviceForm")
    public String showServiceForm(Model model) {
        model.addAttribute("serviceRequest", new ServiceRequest());
        return "serviceForm";
    }

    @PostMapping("/submitServiceForm")
    public String submitServiceForm(@ModelAttribute("serviceRequest") ServiceRequest serviceRequest, Model model) {
        List<DesignEntity> suitableDesigns;

        if ("Maintenance".equalsIgnoreCase(serviceRequest.getServiceType())) {
            suitableDesigns = designService.findSuitableMaintenanceDesigns(serviceRequest);
        } else if ("Cleaning".equalsIgnoreCase(serviceRequest.getServiceType())) {
            suitableDesigns = designService.findSuitableCleaningDesigns(serviceRequest);
        } else {
            suitableDesigns = List.of(); // Trả về danh sách trống nếu loại dịch vụ không hợp lệ
        }

        model.addAttribute("designs", suitableDesigns);
        return "Availableservice"; // Đảm bảo rằng Availableservice.html tồn tại trong thư mục templates
    }

    @PostMapping("/submitSelectedDesigns")
    public String submitSelectedDesigns(@RequestParam(value = "selectedDesignIds", required = false) List<Long> selectedDesignIds,
                                        @RequestParam("action") String action,
                                        Model model,
                                        HttpSession session) {
        if (selectedDesignIds == null || selectedDesignIds.isEmpty()) {
            model.addAttribute("errorMessage", "Please select at least one service to proceed.");
            List<DesignEntity> suitableDesigns = designService.getAllAvailableDesigns();
            model.addAttribute("designs", suitableDesigns);
            return "Availableservice";
        }

        List<DesignEntity> selectedDesigns = designService.getDesignsByIds(selectedDesignIds);

        if ("payment".equals(action)) {
            Map<DesignEntity, Integer> designItems = selectedDesigns.stream()
                    .collect(Collectors.toMap(design -> design, design -> 1)); // Mặc định mỗi dịch vụ có số lượng là 1

            // Lưu vào session với tên "designItems" cho dễ truy xuất trong PaymentController
            session.setAttribute("designItems", designItems);

            model.addAttribute("selectedDesigns", selectedDesigns);
            return "Payment";
        }

        model.addAttribute("designs", selectedDesigns);
        return "Availableservice";
    }


}
