package com.example.swp391.controller;

import com.example.swp391.entity.*;
import com.example.swp391.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DesignService designService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private RatingFeedbackService ratingFeedbackService;

    @Autowired
    private StaffProjectService staffProjectService;

    // Display customer dashboard with design list
    @GetMapping("/customer/dashboard")
    public String getCustomerDashboard(HttpSession session, Model model) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
        if (account == null) {
            model.addAttribute("errorMessage", "You need to log in to view the dashboard.");
            return "errorPage";
        }

        CustomerEntity customer = account.getCustomer();
        if (customer == null) {
            model.addAttribute("errorMessage", "Customer does not exist.");
            return "errorPage";
        }

        List<DesignEntity> designs = designService.findNeedToPaymentDesignsByCustomerReference(customer.getCustomerID());

        model.addAttribute("customer", customer);
        model.addAttribute("designs", designs);
        return "customerDashboard";
    }

    // Display design details
    @GetMapping("/customer/design/{designId}")
    public String viewDesignDetails(@PathVariable Long designId, HttpSession session, Model model) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
        if (account == null || account.getCustomer() == null) {
            model.addAttribute("errorMessage", "You need to log in to view the design details.");
            return "errorPage";
        }

        DesignEntity design = designService.findById(designId);
        if (design == null || !design.getCustomerReference().equals(account.getCustomer().getCustomerID())) {
            model.addAttribute("errorMessage", "The design does not exist or you do not have permission to access it.");
            return "errorPage";
        }

        model.addAttribute("design", design);
        return "customerDesignDetails";
    }

    // Display payment page for design
    @GetMapping("/customer/design/{designId}/payment")
    public String showPaymentPage(@PathVariable Long designId, HttpSession session, Model model) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
        if (account == null || account.getCustomer() == null) {
            model.addAttribute("errorMessage", "You need to log in to proceed with payment.");
            return "errorPage";
        }

        DesignEntity design = designService.findById(designId);
        if (design == null || !design.getCustomerReference().equals(account.getCustomer().getCustomerID())) {
            model.addAttribute("errorMessage", "The design does not exist or you do not have permission to pay.");
            return "errorPage";
        }

        if (!"NeedToPayment".equals(design.getStatus().name())) {
            model.addAttribute("errorMessage", "This design is currently not available for payment.");
            return "errorPage";
        }

        Map<DesignEntity, Integer> designForPayment = new HashMap<>();
        designForPayment.put(design, 1);
        session.setAttribute("designForPayment", designForPayment);

        model.addAttribute("designforproject", design);
        model.addAttribute("paymentAmount", design.getPrice());
        return "paymentPage";
    }

    @GetMapping("account/customer/customer-projects")
    public String viewCustomerProjects(Model model, HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");

        if (account == null || account.getCustomer() == null) {
            model.addAttribute("errorMessage", "You need to log in to proceed with payment.");
            return "errorPage";
        }

        Long customerId = account.getCustomer().getCustomerID();

        List<ProjectEntity> projects = projectService.getIncompleteProjectsByCustomerId(customerId);
        model.addAttribute("projects", projects);

        return "customer-projects";
    }

    @PostMapping("/submitFeedback")
    public ResponseEntity<String> submitFeedback(@RequestParam Long projectId,
                                                 @RequestParam int rating,
                                                 @RequestParam String feedback) {
        ProjectEntity project = projectService.getProjectById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + projectId));

        if (!"COMPLETE".equalsIgnoreCase(project.getStatus())) {
            return ResponseEntity.badRequest().body("Only completed projects can be rated.");
        }

        RatingFeedbackEntity existingFeedback = ratingFeedbackService.findByProjectId(projectId);
        if (existingFeedback != null && existingFeedback.isReviewed()) {
            return ResponseEntity.badRequest().body("This project has already been rated and cannot be modified.");
        }

        RatingFeedbackEntity ratingFeedback = new RatingFeedbackEntity();
        ratingFeedback.setProject(project);
        ratingFeedback.setCustomer(project.getCustomer());
        ratingFeedback.setRating(rating);
        ratingFeedback.setFeedback(feedback);
        ratingFeedback.setReviewed(true);
        ratingFeedbackService.saveFeedback(ratingFeedback);

        return ResponseEntity.ok("Your feedback has been submitted successfully.");
    }

//    @GetMapping("/account/customer/completed-projects")
//    public String viewCompletedProjects(Model model, HttpSession session) {
//        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
//
//        if (account == null || account.getCustomer() == null) {
//            model.addAttribute("errorMessage", "You need to log in to view project history.");
//            return "errorPage";
//        }
//
//        Long customerId = account.getCustomer().getCustomerID();
//        List<ProjectEntity> completedProjects = projectService.getCompletedProjectsByCustomerId(customerId);
//        model.addAttribute("completedProjects", completedProjects);
//
//        return "completed-projects";
//    }
    // Xử lý từ chối thiết kế
    @PostMapping("/customer/design/{designId}/reject")
    public String rejectDesign(@PathVariable Long designId, HttpSession session, RedirectAttributes redirectAttributes) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
        if (account == null || account.getCustomer() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You need to log in to reject the design.");
            return "redirect:/customer/dashboard";
        }

        DesignEntity design = designService.findById(designId);
        if (design == null || !design.getCustomerReference().equals(account.getCustomer().getCustomerID())) {
            redirectAttributes.addFlashAttribute("errorMessage", "The design does not exist or you do not have permission to reject it.");
            return "redirect:/customer/dashboard";
        }

        if (!"NeedToPayment".equals(design.getStatus().name())) {
            redirectAttributes.addFlashAttribute("errorMessage", "This design is not available for rejection.");
            return "redirect:/customer/dashboard";
        }

        // Cập nhật trạng thái về Designed nếu khách hàng từ chối
        design.setStatus(DesignEntity.Status.Pending);
        designService.save(design);

        redirectAttributes.addFlashAttribute("successMessage", "Design has been rejected successfully.");
        return "redirect:/customer/dashboard";
    }

    @GetMapping("/customer/project/{projectId}")
    public String viewCustomerProjectDetails(@PathVariable Long projectId, HttpSession session, Model model) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");

        if (account == null || account.getCustomer() == null) {
            model.addAttribute("errorMessage", "You need to log in to view the project details.");
            return "errorPage";
        }

        ProjectEntity project = projectService.findById(projectId);

        // Kiểm tra xem dự án có thuộc về khách hàng hiện tại không
        if (!Long.valueOf(project.getCustomer().getCustomerID()).equals(account.getCustomer().getCustomerID())
        ) {
            model.addAttribute("errorMessage", "You do not have permission to view this project.");
            return "errorPage";
        }

        List<StaffProjectEntity> tasks = staffProjectService.getTasksByProjectId(projectId);

        model.addAttribute("project", project);
        model.addAttribute("tasks", tasks);
        return "viewCustomerProject"; // Tên file HTML của trang chi tiết dự án cho khách hàng
    }
    @GetMapping("/account/customer/completed-projects")
    public String viewAllProjects(Model model, HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");

        if (account == null || account.getCustomer() == null) {
            model.addAttribute("errorMessage", "You need to log in to view your projects.");
            return "errorPage";
        }

        Long customerId = account.getCustomer().getCustomerID();
        List<ProjectEntity> allProjects = projectService.getAllProjectsByCustomerId(customerId);
        model.addAttribute("allProjects", allProjects);

        return "completed-projects";  // Tên của view cho trang hiển thị tất cả dự án
    }
}