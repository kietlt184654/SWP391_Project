package com.example.swp391.controller;

import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.entity.StaffEntity;
import com.example.swp391.entity.StaffProjectEntity;
import com.example.swp391.service.ProjectService;
import com.example.swp391.service.StaffProjectService;
import com.example.swp391.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")

public class TaskController {
    @Controller
    @RequestMapping("/staff")
    public class StaffController {

        @Autowired
        private StaffService staffService;

        @GetMapping("/list")
        public String showStaffList(Model model) {
            // Lấy danh sách các nhân viên có role là "construction staff"
            List<StaffEntity> staffList = staffService.getConstructionStaff();
            model.addAttribute("staffList", staffList);
            return "viewDetailProject"; // Tên của view hiển thị danh sách Staff
        }

        @PostMapping("/assign")
        public String assignTask(@RequestParam("staffId") int staffId,
                                 @RequestParam("taskDescription") String taskDescription,
                                 Model model) {
            Optional<StaffEntity> selectedStaff = staffService.getStaffById(staffId);
            if (selectedStaff.isPresent()) {
                // Xử lý logic thêm task cho staff hoặc các bước cần thiết khác
                model.addAttribute("selectedStaff", selectedStaff.get());
                model.addAttribute("taskDescription", taskDescription);
                model.addAttribute("successMessage", "Task assigned successfully to " + selectedStaff.get().getAccount().getAccountName());
            } else {
                model.addAttribute("errorMessage", "Staff not found");
            }
            return "viewDetailProject"; // Trang xác nhận khi gán task
        }
    }

}
