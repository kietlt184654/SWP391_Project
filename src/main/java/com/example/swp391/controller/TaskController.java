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

import java.util.List;
import java.util.Optional;

@Controller


public class TaskController {
    @Autowired
    private StaffService staffService;

    @GetMapping("/staff/list")
   public String listStaff(Model model) {
        List<StaffEntity> staffList = staffService.getStaffByRole("Construction Staff");
        model.addAttribute("staffList", staffList);
        return "staffList";
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


}
