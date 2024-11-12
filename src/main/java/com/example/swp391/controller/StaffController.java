package com.example.swp391.controller;


import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.StaffEntity;
import com.example.swp391.repository.AccountRepository;
import com.example.swp391.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AccountRepository accountRepository;

    // 1. Display list of consulting and construction staff
    @GetMapping("/lists")
    public String listStaff(Model model) {
        List<StaffEntity> staffList = staffRepository.findByAccount_AccountTypeIDIn(List.of("Consulting Staff", "Construction Staff", "Design Staff"));
        model.addAttribute("staffList", staffList);
        return "lists"; // Return Thymeleaf template for displaying staff list
    }

    // 2. Show form to create a new staff account
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("staff", new StaffEntity());
        model.addAttribute("accountTypes", List.of("Consulting Staff", "Construction Staff"));
        return "create"; // Return Thymeleaf template for staff creation form
    }

    // 3. Handle form submission for creating a new staff account
    @PostMapping("/create")
    public String createStaff(@ModelAttribute StaffEntity staff, RedirectAttributes redirectAttributes) {
        AccountEntity account = staff.getAccount();
        if (!List.of("Consulting Staff", "Construction Staff").contains(account.getAccountTypeID())) {
            redirectAttributes.addFlashAttribute("error", "Account type must be either 'Consulting Staff' or 'Construction Staff'");
            return "redirect:/staff/create";
        }
        accountRepository.save(account); // Save the account
        staffRepository.save(staff); // Then save the staff
        redirectAttributes.addFlashAttribute("success", "Staff account created successfully");
        return "redirect:/staff/lists";
    }

    // 4. Delete a staff account by StaffID
    @GetMapping("/delete/{staffID}")
    public String deleteStaff(@PathVariable Integer staffID, RedirectAttributes redirectAttributes) {
        staffRepository.deleteById(staffID);
        redirectAttributes.addFlashAttribute("success", "Staff deleted successfully");
        return "redirect:/staff/list";
    }
}
