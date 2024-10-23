package com.example.swp391.controller;

import com.example.swp391.entity.*;
import com.example.swp391.service.MaterialService;
import com.example.swp391.service.PaymentService;
import com.example.swp391.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ProjectService projectService;

    // Các giá trị cấu hình cho VNPay
    @Value("${vnpay.tmncode}")
    private String vnp_TmnCode;

    @Value("${vnpay.hashsecret}")
    private String vnp_HashSecret;

    @Value("${vnpay.vnpay_api_url}")
    private String vnp_Url;

    @Value("${vnpay.return_url}")
    private String vnp_ReturnUrl;
    @Autowired
    private MaterialService materialService;

    /**
     * Xử lý yêu cầu tạo thanh toán và chuyển hướng đến VNPay hoặc xử lý COD
     */
    @PostMapping("/payment/create")
    public String createPayment(HttpSession session,
                                @RequestParam("payment-method") String paymentMethod,
                                Model model) {
        // Lấy giỏ hàng từ session
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart == null || cart.getDesignItems().isEmpty()) {
            model.addAttribute("message", "Giỏ hàng trống.");
            return "error";  // Quay lại nếu giỏ hàng trống
        }

        // Kiểm tra nguyên vật liệu trước khi thanh toán
        if (!materialService.checkMaterialsForCart(cart)) {
            model.addAttribute("message", "Không đủ nguyên vật liệu cho sản phẩm trong giỏ hàng.");
            return "error";  // Không thể thanh toán do thiếu nguyên vật liệu
        }

        // Tính tổng số tiền từ giỏ hàng
        double totalAmount = cart.getTotalPrice();

        // Xử lý theo từng phương thức thanh toán
        if ("bank-transfer".equals(paymentMethod)) {
            return handleBankTransferPayment(cart, totalAmount, model);
        } else if ("cod".equals(paymentMethod)) {
            return handleCodPayment(cart, session, model);  // Truyền session vào phương thức
        } else {
            model.addAttribute("message", "Phương thức thanh toán không hợp lệ.");
            return "error";
        }
    }

    /**
     * Xử lý thanh toán qua ngân hàng (VNPay)
     */
    private String handleBankTransferPayment(CartEntity cart, double totalAmount, Model model) {
        // Tạo các tham số cho VNPay
        Map<String, String> vnp_Params = createVnpParams(cart, totalAmount);

        // Tạo URL query và mã hóa SecureHash
        String queryUrl = vnp_Params.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));
        String secureHash = hmacSHA512(vnp_HashSecret, queryUrl);

        // Chuyển hướng người dùng tới VNPay
        return "redirect:" + vnp_Url + "?" + queryUrl + "&vnp_SecureHash=" + secureHash;
    }

    /**
     * Xử lý thanh toán COD (thanh toán khi nhận hàng)
     */
    private String handleCodPayment(CartEntity cart, HttpSession session, Model model) {
        // Ghi nhận yêu cầu thanh toán COD và cập nhật trạng thái thanh toán
        PaymentEntity payment = new PaymentEntity();
        payment.setTransactionId("COD-" + System.currentTimeMillis());  // Sử dụng thời gian hiện tại làm ID tạm thời
        payment.setAmount(cart.getTotalPrice());
        payment.setPaymentDate(new java.util.Date());
        payment.setPaymentStatus("Pending COD");

        // Lưu bản ghi thanh toán vào cơ sở dữ liệu
        paymentService.savePayment(payment);
        // Sau khi thanh toán thành công, tạo dự án cho từng sản phẩm trong giỏ hàng
        materialService.updateMaterialsAfterCheckout(cart);
        createProjectsFromCart(cart, session);

        // Xóa giỏ hàng sau khi thanh toán COD
        session.removeAttribute("cart");

        // Thông báo thành công
        model.addAttribute("message", "Yêu cầu thanh toán COD đã được ghi nhận.");
        return "codSuccess";
    }

    /**
     * Xử lý kết quả thanh toán từ VNPay (khi người dùng hoàn tất thanh toán)
     */
    @GetMapping("/payment/vnpay-return")
    public String paymentReturn(@RequestParam Map<String, String> allRequestParams, Model model, HttpSession session) {
        Map<String, String> vnp_Params = new HashMap<>(allRequestParams);

        // Lấy SecureHash để xác thực
        String vnp_SecureHash = vnp_Params.remove("vnp_SecureHash");
        String queryString = vnp_Params.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        // Xác thực chữ ký
        String signValue = hmacSHA512(vnp_HashSecret, queryString);

        if (signValue.equals(vnp_SecureHash)) {
            String responseCode = vnp_Params.get("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                // Thanh toán thành công, lưu thông tin vào DB
                double amount = Double.parseDouble(vnp_Params.get("vnp_Amount")) / 100;
                String transactionId = vnp_Params.get("vnp_TransactionNo");

                // Tạo bản ghi thanh toán
                PaymentEntity payment = new PaymentEntity();
                payment.setTransactionId(transactionId);
                payment.setAmount(amount);
                payment.setPaymentDate(new java.util.Date());
                payment.setPaymentStatus("Success");

                // Lưu thanh toán
                paymentService.savePayment(payment);

                // Xóa giỏ hàng sau khi thanh toán thành công
                session.removeAttribute("cart");

                model.addAttribute("message", "Thanh toán thành công!");
                return "success";  // Trả về trang kết quả thanh toán thành công
            } else {
                model.addAttribute("message", "Thanh toán thất bại!");
                return "error";
            }
        } else {
            model.addAttribute("message", "Sai chữ ký!");
            return "error";
        }
    }

    // Phương thức được tách riêng để tạo vnp_Params cho VNPay
    private Map<String, String> createVnpParams(CartEntity cart, double totalAmount) {
        String vnp_TxnRef = String.valueOf(System.currentTimeMillis());  // Mã giao dịch
        String vnp_OrderInfo = "Thanh toán giỏ hàng";
        String vnp_Amount = String.valueOf(totalAmount * 100);  // Chuyển đổi sang VND (nhân 100)
        String vnp_IpAddr = getClientIpAddress();  // Lấy IP của máy khách

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", "billpayment");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        return vnp_Params;
    }

    // Hàm mã hóa HMAC SHA512
    private String hmacSHA512(String key, String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest((data + key).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error while generating HMAC SHA512", e);
        }
    }

    // Hàm để lấy địa chỉ IP của client
    private String getClientIpAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (Exception e) {
            return "127.0.0.1";  // Trả về localhost nếu không thể lấy IP
        }
    }
    private void createProjectsFromCart(CartEntity cart, HttpSession session) {
        // Giả định rằng thông tin khách hàng được lưu trong session
        CustomerEntity customer = (CustomerEntity) session.getAttribute("customer");

        // Duyệt qua các sản phẩm trong giỏ hàng và tạo một dự án cho mỗi sản phẩm
        for (DesignEntity design : cart.getDesignItems()) {
            ProjectEntity project = new ProjectEntity();

            // Set thông tin cho dự án
            project.setName(design.getDesignName() + " Project"); // Tạo tên dự án từ tên sản phẩm
            project.setDescription("Dự án từ sản phẩm " + design.getDesignName());
            project.setTotalCost(design.getPrice()); // Giá của sản phẩm là tổng giá của dự án
            project.setDesign(design);  // Gán sản phẩm (DesignEntity) cho dự án
            project.setCustomerId(customer);  // Gán khách hàng
            java.sql.Date startDate = new java.sql.Date(System.currentTimeMillis());
            project.setStartDate(startDate);  // Ngày bắt đầu dự án (ngày hiện tại)
            project.setStatus("Pending");  // Trạng thái mặc định là đang chờ xử lý

            // Lưu project vào cơ sở dữ liệu (giả sử có ProjectService để lưu)
            projectService.saveProject(project);
        }
    }

}
