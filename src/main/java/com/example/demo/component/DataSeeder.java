package com.example.demo.component;

import com.example.demo.entity.NguoiDung;
import com.example.demo.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Kiểm tra xem trong DB đã có tài khoản "admin" chưa
        // Nếu chưa có (isEmpty) thì mới tạo để tránh lỗi trùng lặp mỗi khi chạy lại code
        if (nguoiDungRepository.findByUsername("admin").isEmpty()) {
            NguoiDung admin = new NguoiDung();
            admin.setUsername("admin");

            // MẬT KHẨU BẮT BUỘC PHẢI MÃ HOÁ TRƯỚC KHI LƯU
            admin.setPassword(passwordEncoder.encode("123456"));

            admin.setRole("ROLE_ADMIN"); // Cấp quyền Admin

            nguoiDungRepository.save(admin);
            System.out.println("✅ Đã tạo tự động tài khoản Admin: admin / 123456");
        }

        // 2. Tạo thêm một tài khoản khách hàng bình thường để lát test thử
        if (nguoiDungRepository.findByUsername("khachhang").isEmpty()) {
            NguoiDung user = new NguoiDung();
            user.setUsername("khachhang");
            user.setPassword(passwordEncoder.encode("1111"));
            user.setRole("ROLE_USER"); // Cấp quyền User bình thường

            nguoiDungRepository.save(user);
            System.out.println("✅ Đã tạo tự động tài khoản User: khachhang / 1111");
        }
    }
}