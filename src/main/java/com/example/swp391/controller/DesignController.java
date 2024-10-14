package com.example.swp391.controller;

import com.example.swp391.entity.DesignEnity;
import com.example.swp391.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/designs")
public class DesignController {

    @Autowired
    private DesignService designService;

    // Hiển thị danh sách tất cả các thiết kế
    @GetMapping("/all")
    public String getAllDesigns(Model model) {
        List<DesignEnity> designs = designService.getAllDesigns(); // Gọi phương thức từ service
        model.addAttribute("designs", designs); // Thêm danh sách vào model để hiển thị trong view
        return "designList"; // Trả về view designList.html hoặc designList.jsp
    }

    // Tìm kiếm thiết kế theo tên và hiển thị kết quả
    @GetMapping("/search")
    public String findDesignByName(@RequestParam("name") String name, Model model) {
        List<DesignEnity> designs = designService.findDesignByName(name); // Gọi phương thức từ service
        model.addAttribute("designs", designs); // Thêm kết quả vào model
        return "designList"; // Trả về view hiển thị danh sách thiết kế
    }

    // Tìm kiếm các mẫu có sẵn theo tên và hiển thị kết quả
    @GetMapping("/available/search")
    public String searchAvailableDesignsByName(@RequestParam("name") String name, Model model) {
        List<DesignEnity> designs = designService.searchAvailableDesignsByName(name); // Gọi phương thức từ service
        model.addAttribute("designs", designs); // Thêm kết quả vào model
        return "designList"; // Trả về view hiển thị danh sách thiết kế có sẵn
    }
    @PostMapping("/processPayment/{projectId}")
    public ResponseEntity<String> processPayment(@PathVariable Long projectId) {
        designService.processPayment(projectId);
        return ResponseEntity.ok("Payment processed and materials updated");
    }
}

