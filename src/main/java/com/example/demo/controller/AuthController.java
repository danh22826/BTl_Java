package com.example.demo.controller;

import com.example.demo.DTO.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Mang username và password đi kiểm tra trong Database (gọi ngầm vào CustomUserDetailsService)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // 2. Nếu đúng, lưu trạng thái "đã đăng nhập" vào bộ nhớ Session
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Trả về cho file JavaScript (frontend) thông tin người dùng bằng JSON
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Đăng nhập thành công!");
            response.put("role", authentication.getAuthorities().iterator().next().getAuthority());
            response.put("username", authentication.getName());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Nếu sai mật khẩu hoặc tài khoản, tự động nhảy vào đây và báo lỗi chữ đỏ 401
            Map<String, String> error = new HashMap<>();
            error.put("message", "Sai tên đăng nhập hoặc mật khẩu");
            return ResponseEntity.status(401).body(error);
        }
    }
}