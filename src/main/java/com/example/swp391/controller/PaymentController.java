package com.example.swp391.controller;

import com.example.swp391.dto.PaymentSource;
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
            @RequestParam("source") PaymentSource source,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getCustomer() == null) {
            redirectAttributes.addFlashAttribute("error", "Please complete your customer information before making a payment.");
            return "redirect:/account/profile";
        }

        CustomerEntity customer = loggedInUser.getCustomer();
        String paymentStatus = "Success";

        switch (source) {
            case CART:
                return processCartPayment(paymentMethod, customer, session, redirectAttributes, paymentStatus);
            case SERVICE:
                return processServicePayment(paymentMethod, customer, session, redirectAttributes, paymentStatus);
            default:
                redirectAttributes.addFlashAttribute("error", "Invalid payment source.");
                return "redirect:/cart/view";
        }
    }

    private String processCartPayment(String paymentMethod, CustomerEntity customer, HttpSession session,
                                      RedirectAttributes redirectAttributes, String paymentStatus) {
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart == null || cart.getDesignItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty.");
            return "redirect:/cart/view";
        }

        if (!materialService.checkMaterialsForCart(cart)) {
            redirectAttributes.addFlashAttribute("error", "Not enough materials in stock for the designs in your cart.");
            return "redirect:/cart/view";
        }

        materialService.updateMaterialsAfterCheckout(cart);
        double discountRate = customerService.calculatePointDiscount(customer);
        double totalAmount = cart.calculateTotalAmount();
        double discountedTotalAmount = totalAmount * (1 - discountRate);
        int totalPointsEarned = projectService.createProjectFromCart(cart, customer, paymentStatus, discountRate);

        storePaymentDetailsInSession(session, totalAmount, discountRate, discountedTotalAmount, totalPointsEarned, paymentStatus, cart.getDesignItems());

        session.removeAttribute("cart"); // Xóa giỏ hàng sau thanh toán
        return handleRedirectAfterPayment(paymentMethod, redirectAttributes);
    }

    private String processServicePayment(String paymentMethod, CustomerEntity customer, HttpSession session,
                                         RedirectAttributes redirectAttributes, String paymentStatus) {
        Map<DesignEntity, Integer> designItems = (Map<DesignEntity, Integer>) session.getAttribute("designItems");
        if (designItems == null || designItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "You have not selected any service.");
            return "redirect:/services/serviceForm";
        }

        if (!materialService.checkMaterialsForDesignItems(designItems)) {
            redirectAttributes.addFlashAttribute("error", "Not enough materials in stock for the selected services.");
            return "redirect:/services/serviceForm";
        }

        materialService.updateMaterialsAfterCheckoutForDesignItems(designItems);
        double discountRate = customerService.calculatePointDiscount(customer);
        double totalAmount = designItems.keySet().stream().mapToDouble(d -> d.getPrice() * designItems.get(d)).sum();
        double discountedTotalAmount = totalAmount * (1 - discountRate);
        int totalPointsEarned = projectService.createProjectFromService(designItems, customer, paymentStatus, discountRate);

        storePaymentDetailsInSession(session, totalAmount, discountRate, discountedTotalAmount, totalPointsEarned, paymentStatus, designItems);

        session.removeAttribute("designItems"); // Xóa danh sách dịch vụ sau thanh toán
        return handleRedirectAfterPayment(paymentMethod, redirectAttributes);
    }

    private void storePaymentDetailsInSession(HttpSession session, double totalAmount, double discountRate,
                                              double discountedTotalAmount, int totalPointsEarned, String paymentStatus,
                                              Map<DesignEntity, Integer> designItems) {
        String transactionId = UUID.randomUUID().toString();
        session.setAttribute("transactionId", transactionId);
        session.setAttribute("totalAmount", String.format("%.2f", totalAmount));
        session.setAttribute("discountRate", String.format("%.2f", discountRate * 100)); // Hiển thị dưới dạng phần trăm
        session.setAttribute("discountAmount", String.format("%.2f", totalAmount - discountedTotalAmount));
        session.setAttribute("discountedTotalAmount", String.format("%.2f", discountedTotalAmount));
        session.setAttribute("pointsEarned", totalPointsEarned);
        session.setAttribute("paymentStatus", paymentStatus);
        session.setAttribute("designItems", designItems);
    }

    private String handleRedirectAfterPayment(String paymentMethod, RedirectAttributes redirectAttributes) {
        if ("cod".equals(paymentMethod)) {
            redirectAttributes.addFlashAttribute("message", "Your order has been placed successfully via COD.");
            return "redirect:/payment/confirmation";
        } else if ("bank-transfer".equals(paymentMethod)) {
            redirectAttributes.addFlashAttribute("message", "Please complete your bank transfer to finalize your order.");
            return "redirect:/payment/instructions";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid payment method selected.");
            return "redirect:/cart/view";
        }
    }

    @Transactional
    @GetMapping("/confirmation")
    public String showOrderConfirmation(Model model, HttpSession session) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null || loggedInUser.getCustomer() == null) {
            return "redirect:/login"; // Chuyển hướng về trang đăng nhập nếu chưa đăng nhập
        }

        String transactionId = (String) session.getAttribute("transactionId");
        String totalAmount = (String) session.getAttribute("totalAmount");
        String discountRate = (String) session.getAttribute("discountRate");
        String discountAmount = (String) session.getAttribute("discountAmount");
        String discountedTotalAmount = (String) session.getAttribute("discountedTotalAmount");
        Integer pointsEarned = (Integer) session.getAttribute("pointsEarned");
        String paymentStatus = (String) session.getAttribute("paymentStatus");

        String formattedTotalAmount = (totalAmount != null) ? totalAmount : "0.00";
        String formattedDiscountRate = (discountRate != null) ? discountRate : "0.00";
        String formattedDiscountAmount = (discountAmount != null) ? discountAmount : "0.00";
        String formattedFinalAmount = (discountedTotalAmount != null) ? discountedTotalAmount : formattedTotalAmount;
        int earnedPoints = (pointsEarned != null) ? pointsEarned : 0;
        String status = (paymentStatus != null) ? paymentStatus : "N/A";

        CustomerEntity customer = loggedInUser.getCustomer();
        int totalPoints = customer.getTotalPoints();

        Map<DesignEntity, Integer> designItems = null;
        try {
            // Kiểm tra session để lấy `designItems` từ `CART` hoặc `SERVICE`
            designItems = (Map<DesignEntity, Integer>) session.getAttribute("designItems");
            if (designItems == null) {
                // Nếu `designItems` chưa tồn tại, kiểm tra xem có thuộc `cart` không
                CartEntity cart = (CartEntity) session.getAttribute("cart");
                if (cart != null) {
                    designItems = cart.getDesignItems();
                }
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

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

        // Xóa session sau khi sử dụng
        session.removeAttribute("transactionId");
        session.removeAttribute("totalAmount");
        session.removeAttribute("discountRate");
        session.removeAttribute("discountAmount");
        session.removeAttribute("discountedTotalAmount");
        session.removeAttribute("pointsEarned");
        session.removeAttribute("paymentStatus");
        session.removeAttribute("designItems");
        session.removeAttribute("cart");

        return "orderConfirmation";
    }
}