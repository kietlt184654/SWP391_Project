//package com.example.swp391.config;
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//@Component
//public class NoCacheFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws java.io.IOException, jakarta.servlet.ServletException {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//        httpResponse.setHeader("Pragma", "no-cache");
//        httpResponse.setHeader("Expires", "0");
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {}
//
//    @Override
//    public void destroy() {}
//}