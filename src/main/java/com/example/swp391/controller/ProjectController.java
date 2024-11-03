package com.example.swp391.controller;

import com.example.swp391.entity.*;
import com.example.swp391.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffProjectService staffProjectService;
    @Autowired
    private AccountService accountService;
@Autowired
private DesignService designService;

    @GetMapping("/showProjects")
    public String showProjects(Model model) {
        List<ProjectEntity> projects = projectService.findAll();  // Giả sử có phương thức lấy tất cả các project
        model.addAttribute("projects", projects);
        return "show_projects";
    }

    @GetMapping("/viewDetailProject/{projectID}")
    public String showDetailProject(@PathVariable Integer projectID, Model model) {
        ProjectEntity project = projectService.getProjectById(projectID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + projectID));

        List<StaffProjectEntity> tasks = staffProjectService.getTasksByProjectId(projectID); // Lấy danh sách tasks
        model.addAttribute("project", project);
        model.addAttribute("projectId", projectID); // Thêm projectId vào model
        model.addAttribute("tasks", tasks); // Thêm danh sách tasks vào model

        return "viewDetailProject"; // Tên của file HTML cho trang chi tiết dự án
    }
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }
    @PostMapping("/submitInvoice")
    public String submitInvoice(
            @RequestParam("customerName") String customerName,
            @RequestParam("email") String email,
            @RequestParam("customerPhone") String customerPhone,
            @RequestParam("customerAddress") String customerAddress,
            @RequestParam("designName") String designName,
            @RequestParam("description") String description,
            @RequestParam("WaterCapacity") float waterCapacity,
            @RequestParam("designType") int designType,
            @RequestParam(value = "img", required = false) String img,
            @RequestParam("price") double price,
            @RequestParam("size") DesignEntity.Size size,
            @RequestParam("ShapeOfPond") String shapeOfPond,
            @RequestParam("estimatedCompletionTime") int estimatedCompletionTime,
            Model model) {

        try {
            // Create a new Account object
            AccountEntity account = new AccountEntity();
            account.setAccountName(customerName);
            account.setEmail(email);
            account.setAccountTypeID("Customer");
            account.setPhoneNumber(customerPhone);
            account.setAddress(customerAddress);
            account.setStatus(true); // Set default status
            account.setPassword(generateRandomPassword()); // Set random password

            // Save the new account to the database
            AccountEntity newAccount = accountService.createAccount(account);

            // Create a new DesignTemplate object
            DesignEntity design = new DesignEntity();
            design.setDesignName(designName);
            design.setDescription(description);
            design.setWaterCapacity(waterCapacity);
            design.setDesignType(String.valueOf(designType));
            design.setImg(img);
            design.setPrice(price);
            design.setSize(size);
            design.setShapeOfPond(shapeOfPond);
            design.setEstimatedCompletionTime(estimatedCompletionTime);
            design.setStatus(DesignEntity.Status.Pending); // Set default status

            // Save the design into the database
            DesignEntity newDesign = designService.createDesign(design);

            model.addAttribute("account", newAccount);
            model.addAttribute("design", newDesign);
            model.addAttribute("messageConsulting", "Invoice submitted successfully.");
        } catch (Exception e) {
            model.addAttribute("messageConsulting", "Failed to submit invoice: " + e.getMessage());
        }

        return "FormConsulting"; // Return a view name for confirmation or failure message
    }







}
