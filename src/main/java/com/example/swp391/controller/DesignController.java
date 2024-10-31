package com.example.swp391.controller;


import com.example.swp391.entity.DesignEntity;
import com.example.swp391.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/design")
public class DesignController {

    @Autowired
    private DesignService designService;
    //    private ImageRepository imgRepo;
//show thiet ke
    @GetMapping("/showAllDesign")
    public String showProducts(Model model) {
        // Lấy danh sách các thiết kế có typeDesignId = 1 từ service
        List<DesignEntity> products = designService.getDesignsByTypeId();
        model.addAttribute("design", products);
        return "Availableproject"; // Trả về trang "Availableproject.html"
    }
    //    // Phương thức tìm kiếm mẫu có sẵn theo tên (chỉ hiển thị mẫu có sẵn)
//    @GetMapping("/search")
//    public String findDesignByName(@RequestParam("name") String name, Model model) {
//        List<DesignEnity> designs = designService.findDesignByName(name); // Gọi phương thức từ service
//        model.addAttribute("designs", designs); // Thêm kết quả vào model
//        return "designList"; // Trả về view hiển thị danh sách các thiết kế
//    }
    // Hiển thị chi tiết sản phẩm
    @GetMapping("/{id}")
    public String getDesignDetail(@PathVariable("id") Long id, Model model) {
        Optional<DesignEntity> designOpt = designService.getProductById(id);
        if (designOpt.isPresent()) {
            DesignEntity design = designOpt.get();
//            List<DesignImgEntity> images = imgRepo.findByDesignId(id);
            model.addAttribute("design", design);
//            model.addAttribute("images", images);
            return "viewProductDetail"; // Tên của template Thymeleaf
        } else {
            return "404"; // Trang lỗi 404 nếu không tìm thấy
        }
    }

}