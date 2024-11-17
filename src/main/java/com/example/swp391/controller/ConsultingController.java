// ConsultingController.java
package com.example.swp391.controller;

import com.example.swp391.entity.DesignEntity;
import com.example.swp391.service.DesignService;
import com.example.swp391.service.TypeDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/HomeConsulting")
public class ConsultingController {

    @Autowired
    private DesignService designService;
    @Autowired
    private TypeDesignService typeDesignService;

    @GetMapping("")
    public String homeConsulting(Model model) {
        // Add any data you need for the HomeConsulting page to the model
        return "consultingHome"; // This should match the name of your HTML file: HomeConsulting.html
    }
    // Hiển thị form thêm thiết kế
    @GetMapping("/add")
    public String showAddDesignForm(Model model) {
        model.addAttribute("design", new DesignEntity()); // Tạo đối tượng DesignEntity rỗng để binding với form
        return "addDesign"; // Trả về trang addDesign.html
    }

    // Xử lý khi nhân viên gửi form
    @PostMapping("/add")
    public String addDesign(@ModelAttribute("design") DesignEntity design, RedirectAttributes redirectAttributes) {
        design.setStatus(DesignEntity.Status.Pending); // Giữ trạng thái ban đầu là Pending
        design.setTypeDesign(typeDesignService.findById(2L));
        designService.save(design);
        redirectAttributes.addFlashAttribute("message", "The design has been submitted for approval.");
        return "consultingHome";
    }


    // Phương thức hiển thị danh sách các thiết kế bị từ chối
    @GetMapping("/rejectedDesigns")
    public String getRejectedDesigns(Model model) {
        List<DesignEntity> rejectedDesigns = designService.findByStatus(DesignEntity.Status.Rejected); // Lấy danh sách các thiết kế bị từ chối
        model.addAttribute("rejectedDesigns", rejectedDesigns); // Đưa danh sách vào model
        return "rejected-Designs"; // Trả về trang rejectedDesigns.html để hiển thị
    }
}
