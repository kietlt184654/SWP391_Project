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

@Controller
public class CartController {

    @Autowired
    private DesignService designService; // Dịch vụ để tìm sản phẩm từ DB

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("designId") Long designId, HttpSession session) {
        // Tìm sản phẩm từ DB theo ID
        DesignEntity design = designService.findDesignById(designId);

        // Lấy giỏ hàng từ session, nếu chưa có thì tạo mới
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartEntity();
            session.setAttribute("cart", cart);
        }

        // Thêm sản phẩm vào giỏ hàng (chỉ với số lượng 1)
        cart.addDesign(design);

        // Điều hướng tới trang giỏ hàng
        return "redirect:/cart/view";
    }

    // Hiển thị giỏ hàng
    @GetMapping("/cart/view")
    public String viewCart(HttpSession session, Model model) {
        // Lấy giỏ hàng từ session
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartEntity();
            session.setAttribute("cart", cart);
        }

        // Tính tổng số tiền
        double totalAmount = cart.calculateTotalAmount();

        // Đưa giỏ hàng và tổng số tiền vào model
        model.addAttribute("cart", cart);
        model.addAttribute("totalAmount", totalAmount);
        return "viewCart"; // Trả về trang hiển thị giỏ hàng
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("designId") Long designId, HttpSession session) {
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart != null) {
            cart.removeDesignById(designId);
            // Optionally remove the cart if it's empty
            if (cart.getDesignItems().isEmpty()) {
                session.removeAttribute("cart");
            }
        }
        return "redirect:/cart/view";
    }
    // Xóa toàn bộ sản phẩm khỏi giỏ hàng
    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        // Lấy giỏ hàng từ session
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart != null) {
            cart.clear();
        }
        return "redirect:/cart/view";
    }

}