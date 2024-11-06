package com.example.swp391.controller;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.service.AccountService;
import com.example.swp391.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.json.JSONObject;

@Controller
public class GoogleCallbackController {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/google/callback")
    public String googleCallback(@RequestParam("code") String code, HttpSession session) {
        try {
            // Lấy Access Token từ Google
            RestTemplate restTemplate = new RestTemplate();
            String tokenUrl = "https://oauth2.googleapis.com/token";
            String tokenRequestBody = "client_id=" + clientId +
                    "&client_secret=" + clientSecret +
                    "&redirect_uri=" + redirectUri +
                    "&grant_type=authorization_code" +
                    "&code=" + code;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> request = new HttpEntity<>(tokenRequestBody, headers);
            ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);

            // Parse Access Token từ phản hồi
            JSONObject tokenJson = new JSONObject(tokenResponse.getBody());
            String accessToken = tokenJson.getString("access_token");

            // Lấy Thông tin Người Dùng bằng Access Token
            String userInfoUrl = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;
            ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, null, String.class);

            // Parse thông tin người dùng
            JSONObject userJson = new JSONObject(userInfoResponse.getBody());
            String email = userJson.getString("email");
            String name = userJson.getString("name");

            // Kiểm tra và tạo tài khoản mới nếu đây là lần đầu đăng nhập
            AccountEntity account = accountService.findByEmail(email);
            if (account == null) {
                account = new AccountEntity();
                account.setEmail(email);
                account.setAccountName(name);
                account.setStatus(true);
                account.setAccountTypeID("Customer");
                accountService.save(account); // Lưu vào cơ sở dữ liệu
            }

            // Đăng nhập người dùng bằng cách lưu vào session
            session.setAttribute("loggedInUser", account);
            // Điều hướng dựa trên vai trò của người dùng
            if (account.getAccountTypeID().equals("Customer")) {
                // Kiểm tra nếu customer chưa tồn tại
                if (!customerService.existsByAccount(account)) {
                    CustomerEntity customer = new CustomerEntity();
                    customer.setAccount(account);
                    customer.setAdditionalInfo("");  // Bạn có thể thêm thông tin bổ sung tùy ý

                    // Lưu Customer mới vào cơ sở dữ liệu
                    customerService.save(customer);
                }
                return "Homepage";
            } else if (account.getAccountTypeID().equals("Manager")) {
                return "manager";
            } else if (account.getAccountTypeID().equals("Consulting Staff")) {
                return "consultingHome";
            } else if (account.getAccountTypeID().equals("Construction Staff")) {
                return "redirect:/dashboard"; // Chuyển hướng đến /dashboard
            }
            return "login";

        } catch (Exception e) {
            e.printStackTrace();
            return "errorPage";
        }
    }
}
