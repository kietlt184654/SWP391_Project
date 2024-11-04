//package com.example.swp391.controller;
//
//import com.example.swp391.entity.AccountEntity;
//import com.example.swp391.entity.CustomerEntity;
//import com.example.swp391.entity.DesignEntity;
//import com.example.swp391.service.CustomerService;
//import com.example.swp391.service.DesignService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
//@Controller
//public class CustomerController {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @Autowired
//    private DesignService designService;
//
//    // Hiển thị bảng điều khiển cho khách hàng với danh sách thiết kế
//    @GetMapping("/customer/dashboard")
//    public String getCustomerDashboard(HttpSession session, Model model) {
//        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
//        if (account == null) {
//            model.addAttribute("errorMessage", "Bạn cần đăng nhập để xem bảng điều khiển.");
//            return "errorPage"; // Trang lỗi hoặc trang đăng nhập
//        }
//
//        CustomerEntity customer = account.getCustomer(); // Lấy thông tin customer từ account trong session
//        if (customer == null) {
//            model.addAttribute("errorMessage", "Khách hàng không tồn tại.");
//            return "errorPage";
//        }
//
//        // Lấy danh sách thiết kế của khách hàng với trạng thái cần thanh toán hoặc có sẵn
//        List<DesignEntity> designs = designService.findDesignsByCustomerReference(customer.getCustomerID());
//        model.addAttribute("customer", customer);
//        model.addAttribute("designs", designs);
//        return "customerDashboard";
//    }
//
//    // Hiển thị chi tiết một thiết kế
//    @GetMapping("/customer/design/{designId}")
//    public String viewDesignDetails(@PathVariable Long designId, HttpSession session, Model model) {
//        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
//        if (account == null || account.getCustomer() == null) {
//            model.addAttribute("errorMessage", "Bạn cần đăng nhập để xem chi tiết thiết kế.");
//            return "errorPage";
//        }
//
//        // Lấy thông tin thiết kế từ ID
//        DesignEntity design = designService.findById(designId);
//        if (design == null || !design.getCustomerReference().equals(account.getCustomer().getCustomerID())) {
//            model.addAttribute("errorMessage", "Thiết kế không tồn tại hoặc bạn không có quyền truy cập.");
//            return "errorPage";
//        }
//
//        model.addAttribute("design", design);
//        return "customerDesignDetails";
//    }
//
//    // Hiển thị trang thanh toán cho thiết kế
//    @GetMapping("/customer/design/{designId}/payment")
//    public String showPaymentPage(@PathVariable Long designId, HttpSession session, Model model) {
//        AccountEntity account = (AccountEntity) session.getAttribute("loggedInUser");
//        if (account == null || account.getCustomer() == null) {
//            model.addAttribute("errorMessage", "Bạn cần đăng nhập để tiến hành thanh toán.");
//            return "errorPage";
//        }
//
//        DesignEntity design = designService.findById(designId);
//        if (design == null || !design.getCustomerReference().equals(account.getCustomer().getCustomerID())) {
//            model.addAttribute("errorMessage", "Thiết kế không tồn tại hoặc bạn không có quyền thanh toán.");
//            return "errorPage";
//        }
//
//        if (!"Available".equals(design.getStatus().name())) {
//            model.addAttribute("errorMessage", "Thiết kế này hiện không cần thanh toán.");
//            return "errorPage";
//        }
//
//        model.addAttribute("design", design);
//        model.addAttribute("paymentAmount", design.getPrice());
//        return "paymentPage";
//    }
//}
