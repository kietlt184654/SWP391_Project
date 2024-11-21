//package com.example.swp391.config;
//
//import com.example.swp391.entity.AccountEntity;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Component
//public class SessionInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
//
//        // Kiểm tra nếu chưa đăng nhập hoặc không phải Customer/Guest
//        if (loggedInUser == null || (!"Customer".equals(loggedInUser.getAccountTypeID()) && !"Guest".equals(loggedInUser.getAccountTypeID()))) {
//            response.sendRedirect("/login"); // Chuyển hướng đến trang đăng nhập
//            return false;
//        }
//
//        return true; // Cho phép tiếp tục xử lý request
//    }
//}
