package com.example.swp391.controller;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.CartEntity;
import com.example.swp391.service.MaterialService;
import com.example.swp391.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private MaterialService materialService;
    @Autowired
    private ProjectService  projectService;

    @Transactional
    @PostMapping("/create")
    public String createPayment(
            @RequestParam("payment-method") String paymentMethod,
            HttpSession session,
            Model model,
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

        // Xử lý thanh toán dựa trên phương thức đã chọn
        if ("cod".equals(paymentMethod)) {
            String transactionId = UUID.randomUUID().toString(); // Mã giao dịch ngẫu nhiên
            String paymentStatus = "Success"; // Có thể thay đổi dựa trên kết quả thanh toán
            materialService.updateMaterialsAfterCheckout(cart);
            projectService.createProjectFromCart(cart, loggedInUser.getCustomer(), paymentStatus, transactionId);
            session.removeAttribute("cart"); // Xóa giỏ hàng sau khi thanh toán thành công
            redirectAttributes.addFlashAttribute("message", "Your order has been placed successfully via COD.");
            return "redirect:/order/confirmation"; // Điều hướng đến trang xác nhận đơn hàng

        } else if ("bank-transfer".equals(paymentMethod)) {
            String transactionId = UUID.randomUUID().toString(); // Mã giao dịch ngẫu nhiên
            String paymentStatus = "Success"; // Có thể thay đổi dựa trên kết quả thanh toán
            materialService.updateMaterialsAfterCheckout(cart);
            projectService.createProjectFromCart(cart, loggedInUser.getCustomer(), paymentStatus, transactionId);
            session.removeAttribute("cart"); // Xóa giỏ hàng sau khi thanh toán thành công
            redirectAttributes.addFlashAttribute("message", "Please complete your bank transfer to finalize your order.");
            return "redirect:/payment/instructions"; // Điều hướng đến trang hướng dẫn thanh toán qua ngân hàng
        }

        // Nếu không có phương thức thanh toán hợp lệ, trả về lỗi
        redirectAttributes.addFlashAttribute("error", "Invalid payment method selected.");
        return "redirect:/cart/view";
    }
}
