package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // BƯỚC MỚI 1: Bộc lộ AuthenticationManager để lát nữa tự gọi trong Controller
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tạm tắt chống giả mạo để gọi API dễ dàng
                .authorizeHttpRequests(auth -> auth
                        // BƯỚC MỚI 2: Mở cửa thêm cho đường dẫn /api/auth/** để ai cũng có thể gọi hàm đăng nhập
                        .requestMatchers("/css/**","/", "/js/**", "/poster/**", "/html/**", "/api/auth/**").permitAll()

                        // Các luật cũ giữ nguyên
                        .requestMatchers(HttpMethod.POST, "/api/phim/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/phim/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/phim/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/phim/**").permitAll()

                        .anyRequest().authenticated()
                );
        // BƯỚC MỚI 3: Đã XÓA TOÀN BỘ đoạn .formLogin(...) ở đây

        return http.build();
    }
}