package com.example.swp391.controller;

import com.example.swp391.entity.*;
import com.example.swp391.service.CustomerService;
import com.example.swp391.service.DesignService;
import com.example.swp391.service.ProjectService;
import com.example.swp391.service.RatingFeedbackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    // Hiển thị bảng điều khiển cho khách hàng với danh sách thiết kế
    @GetMapping("/customer/dashboard")
    public String getCustomerDashboard(HttpSession session, Model model) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
        if (account == null) {
            model.addAttribute("errorMessage", "Bạn cần đăng nhập để xem bảng điều khiển.");
            return "errorPage"; // Trang lỗi hoặc trang đăng nhập
        }

        CustomerEntity customer = account.getCustomer(); // Lấy thông tin customer từ account trong session
        if (customer == null) {
            model.addAttribute("errorMessage", "Khách hàng không tồn tại.");
            return "errorPage";
        }

        // Lấy danh sách thiết kế của khách hàng với trạng thái cần thanh toán hoặc có sẵn
        List<DesignEntity> designs = designService.findNeedToPaymentDesignsByCustomerReference(customer.getCustomerID());

        model.addAttribute("customer", customer);
        model.addAttribute("designs", designs);
        return "customerDashboard";
    }

    // Hiển thị chi tiết một thiết kế
    @GetMapping("/customer/design/{designId}")
    public String viewDesignDetails(@PathVariable Long designId, HttpSession session, Model model) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
        if (account == null || account.getCustomer() == null) {
            model.addAttribute("errorMessage", "Bạn cần đăng nhập để xem chi tiết thiết kế.");
            return "errorPage";
        }

        // Lấy thông tin thiết kế từ ID
        DesignEntity design = designService.findById(designId);
        if (design == null || !design.getCustomerReference().equals(account.getCustomer().getCustomerID())) {
            model.addAttribute("errorMessage", "Thiết kế không tồn tại hoặc bạn không có quyền truy cập.");
            return "errorPage";
        }

        model.addAttribute("design", design);
        return "customerDesignDetails";
    }

    // Hiển thị trang thanh toán cho thiết kế
    @GetMapping("/customer/design/{designId}/payment")
    public String showPaymentPage(@PathVariable Long designId, HttpSession session, Model model) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
        if (account == null || account.getCustomer() == null) {
            model.addAttribute("errorMessage", "Bạn cần đăng nhập để tiến hành thanh toán.");
            return "errorPage";
        }

        DesignEntity design = designService.findById(designId);
        if (design == null || !design.getCustomerReference().equals(account.getCustomer().getCustomerID())) {
            model.addAttribute("errorMessage", "Thiết kế không tồn tại hoặc bạn không có quyền thanh toán.");
            return "errorPage";
        }

        if (!"NeedToPayment".equals(design.getStatus().name())) {
            model.addAttribute("errorMessage", "Thiết kế này hiện không cần thanh toán.");
            return "errorPage";
        }

        // Lưu `design` vào session để sử dụng trong thanh toán
        // Tạo `Map<DesignEntity, Integer>` với một phần tử là `design` và giá trị 1 cho số lượng
        Map<DesignEntity, Integer> designForPayment = new HashMap<>();
        designForPayment.put(design, 1); // Đặt số lượng là 1 vì chỉ thanh toán cho một thiết kế
        // Lưu vào session để sử dụng trong `PaymentController`
        session.setAttribute("designForPayment", designForPayment);


        model.addAttribute("designforproject", design);
        model.addAttribute("paymentAmount", design.getPrice());
        return "paymentPage";
    }
    @GetMapping("account/customer/customer-projects")
    public String viewCustomerProjects(Model model, HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");

        if (account == null || account.getCustomer() == null) {
            model.addAttribute("errorMessage", "Bạn cần đăng nhập để tiến hành thanh toán.");
            return "errorPage";
        }

        // Lấy customerID từ tài khoản trong session
        Long customerId = account.getCustomer().getCustomerID();

        List<ProjectEntity> projects = projectService.getIncompleteProjectsByCustomerId(customerId);
        model.addAttribute("projects", projects);

        return "customer-projects"; // Trỏ đến trang Thymeleaf để hiển thị danh sách dự án
    }


    @PostMapping("/submitFeedback")
    public ResponseEntity<String> submitFeedback(@RequestParam Long projectId,
                                                 @RequestParam int rating,
                                                 @RequestParam String feedback) {
        ProjectEntity project = projectService.getProjectById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + projectId));

        if (!"COMPLETE".equalsIgnoreCase(project.getStatus())) {
            return ResponseEntity.badRequest().body("Chỉ những dự án hoàn thành mới có thể đánh giá.");
        }

        // Kiểm tra nếu dự án đã được đánh giá trước đó
        RatingFeedbackEntity existingFeedback = ratingFeedbackService.findByProjectId(projectId);
        if (existingFeedback != null && existingFeedback.isReviewed()) {
            return ResponseEntity.badRequest().body("Dự án này đã được đánh giá và không thể chỉnh sửa.");
        }

        // Tạo và lưu đối tượng RatingFeedbackEntity
        RatingFeedbackEntity ratingFeedback = new RatingFeedbackEntity();
        ratingFeedback.setProject(project);
        ratingFeedback.setCustomer(project.getCustomer());
        ratingFeedback.setRating(rating);
        ratingFeedback.setFeedback(feedback);
        ratingFeedback.setReviewed(true);  // Đánh dấu là đã được đánh giá
        ratingFeedbackService.saveFeedback(ratingFeedback);

        return ResponseEntity.ok("Đánh giá của bạn đã được gửi thành công.");
    }
    @GetMapping("/account/customer/completed-projects")
    public String viewCompletedProjects(Model model, HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");

        if (account == null || account.getCustomer() == null) {
            model.addAttribute("errorMessage", "Bạn cần đăng nhập để xem lịch sử dự án.");
            return "errorPage";
        }

        Long customerId = account.getCustomer().getCustomerID();
        List<ProjectEntity> completedProjects = projectService.getCompletedProjectsByCustomerId(customerId);
        model.addAttribute("completedProjects", completedProjects);

        return "completed-projects"; // Trang để hiển thị lịch sử dự án hoàn thành
    }
}
