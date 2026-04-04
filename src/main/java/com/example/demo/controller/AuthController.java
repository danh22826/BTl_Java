package com.example.demo.controller;

import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Cho phép Frontend (chạy ở cổng khác) gọi được API này
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> loginRequest) {
        // 1. Xác thực tài khoản với username và password gửi từ Frontend
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.get("username"),
                        loginRequest.get("password")
                )
        );

        // Lưu trạng thái đã xác thực vào Spring Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2. Nhờ JwtUtils tạo Token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // 3. Trích xuất thông tin User (từ file CustomUserDetails)
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        // 4. MẸO XỬ LÝ MÃ KHÁCH HÀNG (Do bảng NguoiDung của bạn đang chưa nối với KhachHang)
        String maKhachHang;
        if (userDetails.getUsername().equals("tester")) {
            maKhachHang = "KHTEST"; // Map với mã khách hàng vừa tạo trong SQL
        } else {
            maKhachHang = "KH001";  // Dành cho tài khoản "khachhang" cũ
        }

        // 5. Đóng gói JSON trả về cho Frontend
        return ResponseEntity.ok(Map.of(
                "token", jwt,
                "username", userDetails.getUsername(),
                "role", role,
                "maKhachHang", maKhachHang
        ));
    }
}