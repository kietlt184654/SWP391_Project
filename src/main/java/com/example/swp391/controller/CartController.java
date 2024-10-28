package com.example.swp391.controller;

import com.example.swp391.entity.CartEntity;
import com.example.swp391.entity.DesignEntity;
import com.example.swp391.service.DesignService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CartController {

    private final DesignService designService;

    @Autowired
    public CartController(DesignService designService) {
        this.designService = designService;
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("designId") Long designId, HttpSession session, Model model) {
        // Tìm sản phẩm theo ID
        Optional<DesignEntity> designOpt = designService.getDesignById(designId);
        if (!designOpt.isPresent()) {
            model.addAttribute("message", "Sản phẩm không tồn tại.");
            return "error";  // Điều hướng tới trang lỗi nếu không tìm thấy sản phẩm
        }

        DesignEntity design = designOpt.get();

        // Lấy giỏ hàng từ session hoặc tạo mới nếu chưa có
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartEntity();
            session.setAttribute("cart", cart);
        }

        // Thêm sản phẩm vào giỏ hàng
        cart.addDesign(design);

        // Cập nhật giỏ hàng vào session
        session.setAttribute("cart", cart);

        // Chuyển hướng về trang hiển thị giỏ hàng
        return "redirect:/cart/view";
    }

    // Hiển thị giỏ hàng
    @GetMapping("/cart/view")
    public String viewCart(HttpSession session, Model model) {
        // Lấy giỏ hàng từ session
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart == null || cart.getDesignItems().isEmpty()) {
            model.addAttribute("message", "Giỏ hàng trống. Vui lòng thêm sản phẩm.");
            return "viewCart";  // Hiển thị trang giỏ hàng trống
        }

        // Thêm giỏ hàng vào model để hiển thị trong Thymeleaf
        model.addAttribute("cart", cart);

        return "viewCart";  // Trả về trang viewCart.html sử dụng Thymeleaf
    }

    // Hiển thị trang thanh toán
    @GetMapping("/cart/checkout")
    public String checkoutCart(HttpSession session, Model model) {
        // Lấy giỏ hàng từ session
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart == null || cart.getDesignItems().isEmpty()) {
            model.addAttribute("message", "Giỏ hàng trống. Vui lòng thêm sản phẩm trước khi thanh toán.");
            return "redirect:/cart/view";  // Quay lại trang giỏ hàng nếu giỏ hàng trống
        }

        // Tính tổng số tiền của giỏ hàng
        double totalAmount = cart.getDesignItems().stream()
                .mapToDouble(DesignEntity::getPrice)  // Lấy giá của sản phẩm
                .sum();

        // Đưa tổng số tiền vào model để hiển thị trên trang thanh toán
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("cart", cart);

        return "checkoutPage";  // Trả về trang checkoutPage.html
    }

    // Xử lý thanh toán
    @PostMapping("/payment/create")
    public String createPayment(HttpSession session,
                                @RequestParam("payment-method") String paymentMethod,
                                Model model) {
        // Lấy giỏ hàng từ session
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart == null || cart.getDesignItems().isEmpty()) {
            model.addAttribute("message", "Giỏ hàng trống.");
            return "redirect:/cart/view";  // Quay lại trang giỏ hàng nếu giỏ hàng trống
        }

        // Tính tổng số tiền của giỏ hàng
        double totalAmount = cart.getTotalPrice();

        // Xử lý thanh toán
        if ("bank-transfer".equals(paymentMethod)) {
            // Xử lý logic thanh toán ngân hàng (thêm tích hợp VNPay hoặc cổng thanh toán khác)
            model.addAttribute("message", "Chuyển hướng tới trang thanh toán ngân hàng.");
            return "redirect:/bank-transfer-page";  // Chuyển người dùng tới trang thanh toán ngân hàng
        } else if ("cod".equals(paymentMethod)) {
            // Xử lý thanh toán COD (thanh toán khi nhận hàng)
            model.addAttribute("message", "Yêu cầu thanh toán COD đã được ghi nhận.");
            return "codSuccess";  // Trả về trang xác nhận COD thành công
        } else {
            model.addAttribute("message", "Phương thức thanh toán không hợp lệ.");
            return "error";  // Trả về trang lỗi nếu phương thức thanh toán không hợp lệ
        }
    }

}