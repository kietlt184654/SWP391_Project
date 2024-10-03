//package com.example.swp391.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig   {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // Cấu hình mới để tắt CSRF
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/", "/login**", "/error**").permitAll()  // Cho phép các đường dẫn này
//                        .anyRequest().authenticated()  // Các request khác yêu cầu xác thực
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/login")  // Trang đăng nhập tùy chỉnh
//                        .defaultSuccessUrl("/home", true)  // Điều hướng đến /home sau khi đăng nhập thành công
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout") // Đường dẫn để logout
//                        .logoutSuccessUrl("/login?logout")  // Sau khi đăng xuất chuyển về trang login
//                        .invalidateHttpSession(true) // Hủy phiên làm việc (session)
//                        .deleteCookies("JSESSIONID") // Xóa cookie của phiên đăng nhập
//                        .permitAll()
//
//                );
//
//        return http.build();
//
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return null; // Placeholder nếu bạn cần UserDetailsService riêng
//    }
//
//}
