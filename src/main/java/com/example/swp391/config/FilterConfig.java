//package com.example.swp391.config;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//    @Bean
//    public FilterRegistrationBean<NoCacheFilter> noCacheFilter() {
//        FilterRegistrationBean<NoCacheFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new NoCacheFilter());
//        registrationBean.addUrlPatterns("/*"); // Áp dụng cho toàn bộ URL
//        registrationBean.setName("NoCacheFilter");
//        return registrationBean;
//    }
//}