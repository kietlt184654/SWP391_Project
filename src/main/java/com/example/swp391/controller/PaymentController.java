package com.example.swp391.controller;

import com.example.swp391.entity.PaymentEnity;
import com.example.swp391.entity.ProjectEntity;
import com.example.swp391.service.PaymentService;
import com.example.swp391.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class PaymentController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PaymentService paymentService;

    // Hiển thị trang thanh toán
    @GetMapping("/payment")
    public String showPaymentPage(Model model) {
        // Lấy danh sách các dự án cần thanh toán và truyền vào view
        model.addAttribute("projects", projectService.getAllProjects());
        return "payment"; // Trả về view "payment.html" trong thư mục templates
    }

    // Xử lý yêu cầu tạo thanh toán và chuyển hướng đến VNPay
    @GetMapping("/payment/create")
    public String createPayment(@RequestParam("projectId") Long projectId, Model model) {
        ProjectEntity project = projectService.findProjectById(projectId);
        if (project == null) {
            model.addAttribute("message", "Dự án không tồn tại!");
            return "error";
        }

        // Tạo thông tin giao dịch với VNPay
        String vnp_TmnCode = "YOUR_TMNCODE";
        String vnp_HashSecret = "YOUR_HASH_SECRET";
        String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String vnp_ReturnUrl = "http://localhost:8080/payment/vnpay-return";

        // Các thông số thanh toán
        String vnp_TxnRef = String.valueOf(projectId); // Mã giao dịch
        String vnp_OrderInfo = "Thanh toán dự án " + project.getName();
        String vnp_Amount = String.valueOf(project.getTotalCost() * 100); // Đơn vị là VND (x100 vì VNPay yêu cầu)

        // Lấy IP máy chủ để gửi lên VNPay (thay thế cho HttpServletRequest)
        String vnp_IpAddr = getClientIpAddress();

        // Tạo các thông số gửi lên VNPay
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

        // Sắp xếp thông số và mã hóa
        String queryUrl = vnp_Params.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));
        String secureHash = hmacSHA512(vnp_HashSecret, queryUrl);

        // Chuyển hướng người dùng đến VNPay
        return "redirect:" + vnp_Url + "?" + queryUrl + "&vnp_SecureHash=" + secureHash;
    }

    // Xử lý kết quả thanh toán sau khi người dùng thanh toán xong
    @GetMapping("/payment/vnpay-return")
    public String paymentReturn(@RequestParam Map<String, String> allRequestParams, Model model) {
        Map<String, String> vnp_Params = new HashMap<>(allRequestParams);

        // Lấy SecureHash để xác thực
        String vnp_SecureHash = vnp_Params.remove("vnp_SecureHash");
        String queryString = vnp_Params.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        // Xác thực chữ ký
        String signValue = hmacSHA512("YOUR_HASH_SECRET", queryString);

        if (signValue.equals(vnp_SecureHash)) {
            String responseCode = vnp_Params.get("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                // Thanh toán thành công, lưu thông tin vào DB
                Long projectId = Long.parseLong(vnp_Params.get("vnp_TxnRef"));
                double amount = Double.parseDouble(vnp_Params.get("vnp_Amount")) / 100;
                String transactionId = vnp_Params.get("vnp_TransactionNo");

                // Tìm dự án và cập nhật trạng thái thanh toán
                ProjectEntity project = projectService.findProjectById(projectId);
                if (project == null) {
                    model.addAttribute("message", "Dự án không tồn tại");
                    return "error";
                }

                // Tạo bản ghi thanh toán
                PaymentEnity payment = new PaymentEnity();
                payment.setTransactionId(transactionId);
                payment.setAmount(amount);
                payment.setPaymentDate(new java.util.Date());
                payment.setPaymentStatus("Success");
                payment.setProject(project);
//                payment.setCustomer(project.getCustomer());

                // Lưu thông tin thanh toán và cập nhật trạng thái dự án
                paymentService.savePayment(payment);
                project.setStatus("Paid");
                projectService.updateProject(project);

                model.addAttribute("message", "Thanh toán thành công!");
                return "success"; // Trả về trang thông báo thành công
            } else {
                model.addAttribute("message", "Thanh toán thất bại!");
                return "error";
            }
        } else {
            model.addAttribute("message", "Sai chữ ký!");
            return "error";
        }
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

    // Hàm để lấy địa chỉ IP của client (dùng thay cho HttpServletRequest)
    private String getClientIpAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (Exception e) {
            return "127.0.0.1"; // Trả về localhost nếu không thể lấy IP
        }
    }
}
