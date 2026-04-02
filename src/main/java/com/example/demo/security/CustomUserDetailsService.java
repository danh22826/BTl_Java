package com.example.demo.security;

import com.example.demo.entity.NguoiDung;
import com.example.demo.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Tìm user trong database
        NguoiDung user = nguoiDungRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + username));

        // 2. Chuyển đổi thông tin sang chuẩn của Spring Security
        return User.withUsername(user.getUsername())
                .password(user.getPassword()) // Mật khẩu lúc này phải là dạng đã mã hóa
                .authorities(user.getRole())  // Cấp quyền (ROLE_ADMIN hoặc ROLE_USER)
                .build();
    }
}