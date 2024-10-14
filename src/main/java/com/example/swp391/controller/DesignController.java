package com.example.swp391.controller;


import com.example.swp391.entity.DesignEntity;
import com.example.swp391.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DesignController {

    @Autowired
    private DesignService designService;

    @GetMapping("/showAllDesign")
    public String showProducts(Model model) {
        // Lấy danh sách sản phẩm từ service
        List<DesignEntity> products = designService.getAllProducts();
        model.addAttribute("design", products);
        return "Availableproject"; // Trả về trang "Availableproject.html"
    }

}
