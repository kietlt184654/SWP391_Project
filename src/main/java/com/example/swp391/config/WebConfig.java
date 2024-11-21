package com.example.swp391.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///D:/K5/SWP391/Process_Img_Task/");
    }

//    @Autowired
//    private SessionInterceptor sessionInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(sessionInterceptor)
//                .addPathPatterns("/**") // Áp dụng cho tất cả các đường dẫn
//                .excludePathPatterns("/login", "/register", "/css/**", "/js/**"); // Loại trừ các trang không cần kiểm tra
//    }
}
