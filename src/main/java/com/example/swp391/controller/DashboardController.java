package com.example.swp391.controller;

import com.example.swp391.service.DashboardService;
import com.example.swp391.service.DesignService;
import com.example.swp391.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DesignService designService;

    @Autowired
    private ProjectService projectService;
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/total")
    public String showDashboard(Model model) {
        // Lấy dữ liệu cho biểu đồ Design Types
        Map<String, Long> designCountsByTypeName = designService.getDesignCountsByTypeName();
        model.addAttribute("designCountsByTypeName", designCountsByTypeName);

        // Lấy dữ liệu cho biểu đồ Monthly Revenue
        Map<String, Double> monthlyRevenue = dashboardService.getMonthlyRevenue();
        model.addAttribute("monthlyRevenue", monthlyRevenue);

        // Tính tổng doanh thu và thêm vào model
        double totalRevenue = monthlyRevenue.values().stream().mapToDouble(Double::doubleValue).sum();
        model.addAttribute("totalRevenue", totalRevenue);

        return "dashboard-total"; // Trả về một template HTML duy nhất
    }
}