package com.example.swp391.controller;

import com.example.swp391.entity.DesignEntity;
import com.example.swp391.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/designs")
public class DesignController {

    private final DesignService designService;

    // Constructor Injection
    @Autowired
    public DesignController(DesignService designService) {
        this.designService = designService;
    }

    // Hiển thị tất cả các thiết kế
    @GetMapping("/showAllDesign")
    public String showProducts(Model model) {
        // Lấy danh sách thiết kế từ service
        List<DesignEntity> designs = designService.getAllDesigns();
        model.addAttribute("designs", designs);
        return "Availableproject"; // Tên của template HTML để hiển thị danh sách các thiết kế
    }

    // Hiển thị chi tiết một thiết kế cụ thể dựa trên ID
    @GetMapping("/{id}")
    public String getDesignDetail(@PathVariable("id") Long id, Model model) {
        Optional<DesignEntity> designOpt = designService.getProductById(id);
        if (designOpt.isPresent()) {
            // Nếu tìm thấy thiết kế, thêm thông tin vào model và trả về trang chi tiết
            DesignEntity design = designOpt.get();
            model.addAttribute("design", design);
            return "viewProductDetail"; // Tên của template HTML hiển thị chi tiết thiết kế
        } else {
            // Nếu không tìm thấy, trả về trang lỗi 404
            return "404";
        }
    }

    // tìm kiếm theo tên
     @GetMapping("/search")
     public String findDesignByName(@RequestParam("name") String name, Model model) {
         List<DesignEntity> designs = designService.findDesignByName(name);
         model.addAttribute("designs", designs);
         return "designList"; // Tên của template hiển thị kết quả tìm kiếm
     }

}
