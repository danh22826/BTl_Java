package com.example.demo.entity;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Arrays;
@EnableAutoConfiguration
@RestController
@RequestMapping("/api/products") // Cấu hình đường dẫn gốc cho toàn bộ class
public class test {

    // Kích hoạt khi có request: GET http://localhost:8080/api/products
    @GetMapping
    public List<String> getAllProducts() {
        // Trong thực tế, bạn sẽ gọi Database ở đây để lấy dữ liệu
        return Arrays.asList("Laptop Lenovo", "Bàn phím cơ", "Chuột Logitech");
    }
}