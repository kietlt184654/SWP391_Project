package com.example.swp391.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Cấu hình mới để tắt CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login**", "/error**").permitAll()  // Cho phép các đường dẫn này
                        .anyRequest().authenticated()  // Các request khác yêu cầu xác thực
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")  // Trang đăng nhập tùy chỉnh
                        .defaultSuccessUrl("/home", true)  // Điều hướng đến /home sau khi đăng nhập thành công
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return null; // Placeholder nếu bạn cần UserDetailsService riêng
    }
}
