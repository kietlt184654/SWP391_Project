package com.example.swp391.controller;

import com.example.swp391.entity.DesignEnity;
import com.example.swp391.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/designs")
public class DesignController {

    @Autowired
    private DesignService designService;

    // Phương thức hiển thị danh sách tất cả các thiết kế
    @GetMapping("/all")
    public String getAllDesigns(Model model) {
        List<DesignEnity> designs = designService.getAllDesigns(); // Gọi phương thức từ service
        model.addAttribute("designs", designs); // Thêm danh sách vào model để hiển thị trong view
        return "designList"; // Trả về view tên là designList.html hoặc designList.jsp
    }

    // Phương thức tìm kiếm thiết kế theo tên và hiển thị kết quả
    @GetMapping("/search")
    public String findDesignByName(@RequestParam("name") String name, Model model) {
        List<DesignEnity> designs = designService.findDesignByName(name); // Gọi phương thức từ service
        model.addAttribute("designs", designs); // Thêm kết quả vào model
        return "designList"; // Trả về view hiển thị danh sách các thiết kế
    }

    // Phương thức tìm kiếm mẫu có sẵn theo tên (chỉ hiển thị mẫu có sẵn)
    @GetMapping("/available/search")
    public String searchAvailableDesignsByName(@RequestParam("name") String name, Model model) {
        List<DesignEnity> designs = designService.searchAvailableDesignsByName(name); // Tìm kiếm chỉ những mẫu có sẵn
        model.addAttribute("designs", designs); // Thêm kết quả vào model
        return "designList"; // Trả về view để hiển thị kết quả
    }
}
