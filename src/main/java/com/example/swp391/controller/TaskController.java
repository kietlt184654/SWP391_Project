package com.example.swp391.controller;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.entity.StaffEntity;
import com.example.swp391.entity.StaffProjectEntity;
import com.example.swp391.service.AccountService;
import com.example.swp391.service.ProjectService;
import com.example.swp391.service.StaffProjectService;
import com.example.swp391.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller


public class TaskController {
    @Autowired
    private StaffService staffService;
@Autowired
private StaffProjectService projectStaffService;
    @GetMapping("/staff/list")
    public String showStaffList(@RequestParam("projectId") Integer projectId, Model model) {
        // Lấy danh sách staff từ service
        List<StaffEntity> staffList = staffService.getAllStaff();

        // Thêm projectId và staffList vào model
        model.addAttribute("projectId", projectId);
        model.addAttribute("staffList", staffList);

        // Trả về tên view
        return "staffList"; // Tên file staffList.html
    }

    @GetMapping("/staff/add")
    public String addStaff(Model model) {
        model.addAttribute("staff", new StaffEntity());
        return "staff-add";
    }

    @PostMapping("/staff/save")
    public String saveStaff(@ModelAttribute StaffEntity staff) {
        staffService.save(staff);
        return "redirect:/staff/list";
    }

    @GetMapping("/staff/edit/{staffId}")
    public String editStaff(@PathVariable Integer staffId, Model model) {
        StaffEntity staff = staffService.getStaffById(staffId);
        model.addAttribute("staff", staff);
        return "staff-edit";
    }

    @GetMapping("/staff/delete/{staffId}")
    public String deleteStaff(@PathVariable Integer staffId) {
        staffService.deleteStaff(staffId);
        return "redirect:/staff/list";
    }
    @PostMapping("/tasks/project/assign")
    public String assignStaffToProject(
            @RequestParam("projectId") Integer projectId,
            @RequestParam("staffId") Integer staffId,
            @RequestParam("taskDescription") String taskDescription,
            @RequestParam("deadline") String deadline, RedirectAttributes redirectAttributes) {

        // Gọi service để lưu thông tin vào cơ sở dữ liệu
        projectStaffService.assignStaffToProject(projectId, staffId, taskDescription, deadline);
        redirectAttributes.addFlashAttribute("successMessage", "Staff assigned successfully");
        // Chuyển hướng về trang Staff List với projectId
        return "redirect:/staff/list?projectId=" + projectId;
    }

}
