package com.example.swp391.controller;

import com.example.swp391.entity.AccountEntity;
import com.example.swp391.entity.CustomerEntity;
import com.example.swp391.service.AccountService;
import com.example.swp391.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/login")
    public String login(@RequestParam("accountName") String accountname, @RequestParam("password") String password, Model model, HttpSession session) {
        AccountEntity account = accountService.login(accountname, password);
        if (account != null) {
            model.addAttribute("message", "Login Successful");
            session.setAttribute("loggedInUser", account);

            // Kiểm tra và tạo CustomerEntity nếu đây là lần đầu đăng nhập
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
        } else {
            model.addAttribute("messageLogin", "Invalid username or password");
        }
        return "login";
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa toàn bộ session
        session.invalidate();
        // Chuyển hướng đến trang đăng nhập hoặc trang chủ
        return "Homepage"; // Thay đổi đường dẫn theo yêu cầu của bạn
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userDTO") AccountEntity userDTO, Model model) {
        // Kiểm tra email đã tồn tại
        if (accountService.checkIfEmailExists(userDTO.getEmail())) {
            model.addAttribute("emailError", "Email đã được sử dụng");
            return "register";
        }

        // Kiểm tra tên người dùng đã tồn tại
        if (accountService.checkIfAccountNameExists(userDTO.getAccountName())) {
            model.addAttribute("usernameError", "Tên người dùng đã tồn tại");
            return "register";
        }

        // Tạo tài khoản mới và lưu trực tiếp mật khẩu (không mã hóa)
        AccountEntity account = new AccountEntity();
        account.setAccountName(userDTO.getAccountName());
        account.setPassword(userDTO.getPassword()); // Lưu trực tiếp mật khẩu không mã hóa
        account.setEmail(userDTO.getEmail());
        // Lưu tài khoản vào cơ sở dữ liệu
        accountService.registerUser(account);
        return "login"; // Chuyển hướng tới trang đăng nhập sau khi đăng ký thành công
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Lấy thông tin người dùng từ session
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Nếu người dùng chưa đăng nhập, chuyển hướng về trang đăng nhập
            redirectAttributes.addFlashAttribute("message", "Please login first.");
            return "redirect:/login";
        }

        // Lấy thông tin người dùng mới nhất từ cơ sở dữ liệu
        AccountEntity account = accountService.findByEmail(loggedInUser.getEmail());
        model.addAttribute("accountEntity", account);  // Đẩy thông tin người dùng vào model

        return "profile";  // Trả về view "profile.html"
    }

    @GetMapping("/edit-profile")
    public String editProfileForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("message", "Please login first.");
            return "redirect:/login";
        }

        AccountEntity account = accountService.findByEmail(loggedInUser.getEmail());
        model.addAttribute("accountEntity", account);

        return "editprofile"; // Trả về view "edit-profile.html"
    }




    // Đường dẫn lưu trữ hình ảnh trên server
    private final String UPLOAD_DIR = "D:\\K5\\SWP391\\Process_Img_Task";

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam("profileImage") MultipartFile profileImage,
                                @RequestParam("accountName") String accountName,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("address") String address,
                                HttpSession session, RedirectAttributes redirectAttributes) {

        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("message", "Please log in first.");
            return "redirect:/login"; // Điều hướng về trang đăng nhập nếu chưa đăng nhập
        }

        try {
            if (!profileImage.isEmpty()) {
                // Đổi tên file để tránh trùng lặp
                String fileName = System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR, fileName);

                // Lưu file ảnh vào thư mục đích
                Files.createDirectories(uploadPath.getParent()); // Tạo thư mục nếu chưa tồn tại
                Files.write(uploadPath, profileImage.getBytes()); // Ghi file vào đường dẫn

                // Cập nhật đường dẫn ảnh trong cơ sở dữ liệu
                loggedInUser.setImages("/uploads/" + fileName);
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Failed to upload image.");
            e.printStackTrace();
        }

        // Cập nhật các thông tin khác của người dùng
        loggedInUser.setAccountName(accountName);
        loggedInUser.setEmail(email);
        loggedInUser.setPassword(password);  // Nên mã hóa mật khẩu trước khi lưu
        loggedInUser.setPhoneNumber(phoneNumber);
        loggedInUser.setAddress(address);

        // Lưu thông tin đã cập nhật vào cơ sở dữ liệu
        accountService.updateAccount(loggedInUser);

        // Cập nhật session với thông tin mới của người dùng
        session.setAttribute("loggedInUser", loggedInUser);
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");

        return "redirect:/account/profile";  // Quay lại trang profile sau khi lưu thành công
    }


    // Hàm hiển thị danh sách khách hàng
    @GetMapping("/customers")
    public String showAllCustomers(Model model) {
        // Lấy danh sách tất cả khách hàng
        List<AccountEntity> customers = accountService.getAllCustomers();

        // Đưa danh sách vào model để hiển thị trên giao diện
        model.addAttribute("customers", customers);

        // Trả về tên template Thymeleaf
        return "manageCustomer";
    }

    //    @GetMapping("/customers")
//    public String showAllCustomers(Model model) {
//        List<Object[]> customers = customerService.getAllCustomersWithAccountInfo();
//        model.addAttribute("customers", customers);
//        return "manageCustomer";
//    }
    @GetMapping("/dashboardAccount")
    public String showDashboard(Model model) {
        long userCount = accountService.countUsers(); // Lấy số lượng tài khoản từ DB
        model.addAttribute("userCount", userCount); // Gửi dữ liệu sang template Thymeleaf
        return "manager"; // Tên của template HTML (manager.html)
    }


    // Hiển thị trang forgot-password.html
    @GetMapping("/forgot-password-form")
    public String showForgotPasswordForm() {
        return "forgotPassword";  // Trả về view forgot-password.html
    }



    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, Model model) {
        boolean emailExists = accountService.checkIfEmailExistsAndSendResetLink(email);

        if (emailExists) {
            model.addAttribute("successMessage", "A password reset link has been sent to your email.");
        } else {
            model.addAttribute("errorMessage", "Email not found. Please register a new account.");
        }

        return "forgotPassword"; // Trả về lại trang nhập email
    }

    // Hiển thị form đặt lại mật khẩu
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "resetPassword"; // Tên của file HTML trong thư mục templates
    }

    // Xử lý yêu cầu đặt lại mật khẩu sau khi người dùng nhập mật khẩu mới
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("newPassword") String newPassword,
                                Model model) {
        // Gọi service để kiểm tra token và đặt lại mật khẩu
        boolean isResetSuccessful = accountService.resetPassword(token, newPassword);
        if (isResetSuccessful) {
            model.addAttribute("successMessage", "Your password has been reset successfully.");
            return "login"; // Chuyển đến trang đăng nhập sau khi đặt lại thành công
        } else {
            model.addAttribute("errorMessage", "Invalid or expired token.");
            return "resetPassword"; // Quay lại trang đặt lại mật khẩu với thông báo lỗi
        }

    }

}