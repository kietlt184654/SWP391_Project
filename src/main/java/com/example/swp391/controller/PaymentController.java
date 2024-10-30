package com.example.swp391.controller;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.CartEntity;
import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.entity.DesignEntity;
import com.example.swp391.service.CustomerService;
import com.example.swp391.service.MaterialService;
import com.example.swp391.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private MaterialService materialService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CustomerService customerService;

    @Transactional
    @PostMapping("/create")
    public String createPayment(
            @RequestParam("payment-method") String paymentMethod,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // Lấy thông tin người dùng hiện tại từ session
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getCustomer() == null) {
            redirectAttributes.addFlashAttribute("error", "Please complete your customer information before making a payment.");
            return "redirect:/account/profile";
        }

        // Lấy giỏ hàng từ session
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart == null || cart.getDesignItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty.");
            return "redirect:/cart/view";
        }

        // Kiểm tra nguyên vật liệu trong kho
        boolean isStockAvailable = materialService.checkMaterialsForCart(cart);
        if (!isStockAvailable) {
            redirectAttributes.addFlashAttribute("error", "Not enough materials in stock for the designs in your cart.");
            return "redirect:/cart/view";
        }

        // Thiết lập trạng thái thanh toán
        String paymentStatus = "Success";

        // Xử lý thanh toán và cập nhật thông tin
        if ("cod".equals(paymentMethod) || "bank-transfer".equals(paymentMethod)) {
            // Cập nhật kho vật liệu
            materialService.updateMaterialsAfterCheckout(cart);

            // Lấy tổng điểm của khách hàng và tính giảm giá dựa trên điểm
            CustomerEntity customer = loggedInUser.getCustomer();
            double discountRate = customerService.calculatePointDiscount(customer);

            // Tính tổng giá trị của giỏ hàng và giá trị sau khi giảm giá
            double totalAmount = cart.calculateTotalAmount();
            double discountAmount = totalAmount * discountRate;
            double discountedTotalAmount = totalAmount - discountAmount;

            // Tạo dự án từ giỏ hàng và nhận điểm tích lũy
            int totalPointsEarned = projectService.createProjectFromCart(cart, customer, paymentStatus, discountRate);

            // Tạo transactionId ngẫu nhiên cho phiên giao dịch
            String transactionId = UUID.randomUUID().toString();
            // Định dạng các giá trị cần thiết trước khi lưu vào session
            String formattedTotalAmount = String.format("%.2f", totalAmount);
            String formattedDiscountRate = String.format("%.2f", discountRate * 100); // Để hiển thị dạng phần trăm
            String formattedDiscountAmount = String.format("%.2f", discountAmount);
            String formattedDiscountedTotalAmount = String.format("%.2f", discountedTotalAmount);
            // Lưu thông tin thanh toán vào session cho trang xác nhận
            session.setAttribute("transactionId", transactionId);
            session.setAttribute("totalAmount", formattedTotalAmount);
            session.setAttribute("discountRate", formattedDiscountRate);
            session.setAttribute("discountAmount", formattedDiscountAmount);
            session.setAttribute("discountedTotalAmount", formattedDiscountedTotalAmount);
            session.setAttribute("pointsEarned", totalPointsEarned);
            session.setAttribute("paymentStatus", paymentStatus);
            session.setAttribute("designItems", cart.getDesignItems()); // Lưu designItems vào session

            // Xóa giỏ hàng sau khi thanh toán thành công
            session.removeAttribute("cart");

            if ("cod".equals(paymentMethod)) {
                redirectAttributes.addFlashAttribute("message", "Your order has been placed successfully via COD.");
                return "redirect:/payment/confirmation"; // Điều hướng đến trang xác nhận đơn hàng
            } else {
                redirectAttributes.addFlashAttribute("message", "Please complete your bank transfer to finalize your order.");
                return "redirect:/payment/instructions"; // Điều hướng đến trang hướng dẫn thanh toán qua ngân hàng
            }
        }

        // Nếu phương thức thanh toán không hợp lệ
        redirectAttributes.addFlashAttribute("error", "Invalid payment method selected.");
        return "redirect:/cart/view";
    }

    @Transactional
    @GetMapping("/confirmation")
    public String showOrderConfirmation(Model model, HttpSession session) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null || loggedInUser.getCustomer() == null) {
            return "redirect:/login"; // Chuyển hướng về trang đăng nhập nếu chưa đăng nhập
        }

        // Lấy thông tin thanh toán từ session
        String transactionId = (String) session.getAttribute("transactionId");
        String totalAmount = (String) session.getAttribute("totalAmount"); // Đã định dạng
        String discountRate = (String) session.getAttribute("discountRate"); // Đã định dạng (phần trăm)
        String discountAmount = (String) session.getAttribute("discountAmount"); // Đã định dạng
        String discountedTotalAmount = (String) session.getAttribute("discountedTotalAmount"); // Đã định dạng
        Integer pointsEarned = (Integer) session.getAttribute("pointsEarned");
        String paymentStatus = (String) session.getAttribute("paymentStatus");

        // Kiểm tra null và thiết lập giá trị mặc định nếu cần
        String formattedTotalAmount = (totalAmount != null) ? totalAmount : "0.00";
        String formattedDiscountRate = (discountRate != null) ? discountRate : "0.00";
        String formattedDiscountAmount = (discountAmount != null) ? discountAmount : "0.00";
        String formattedFinalAmount = (discountedTotalAmount != null) ? discountedTotalAmount : formattedTotalAmount;
        int earnedPoints = (pointsEarned != null) ? pointsEarned : 0;
        String status = (paymentStatus != null) ? paymentStatus : "N/A";

        // Thông tin khách hàng
        CustomerEntity customer = loggedInUser.getCustomer();
        int totalPoints = customer.getTotalPoints();

        Map<DesignEntity, Integer> designItems = null;
        try {
            designItems = (Map<DesignEntity, Integer>) session.getAttribute("designItems");
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        // Đưa các giá trị vào model để truyền sang giao diện
        model.addAttribute("transactionId", transactionId);
        model.addAttribute("totalAmount", formattedTotalAmount);
        model.addAttribute("discountRate", formattedDiscountRate);
        model.addAttribute("discountAmount", formattedDiscountAmount);
        model.addAttribute("discountedTotalAmount", formattedFinalAmount);
        model.addAttribute("pointsEarned", earnedPoints);
        model.addAttribute("paymentStatus", status);
        model.addAttribute("customerName", loggedInUser.getAccountName());
        model.addAttribute("customerEmail", loggedInUser.getEmail());
        model.addAttribute("totalPoints", totalPoints);
        model.addAttribute("designItems", designItems);

        // Xóa thông tin thanh toán khỏi session sau khi hiển thị xong
        session.removeAttribute("transactionId");
        session.removeAttribute("totalAmount");
        session.removeAttribute("discountRate");
        session.removeAttribute("discountAmount");
        session.removeAttribute("discountedTotalAmount");
        session.removeAttribute("pointsEarned");
        session.removeAttribute("paymentStatus");
        session.removeAttribute("designItems");

        return "orderConfirmation"; // Tên của template Thymeleaf
    }

}
