package com.example.demo.repository;

import com.example.demo.entity.Ghe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GheRepository extends JpaRepository<Ghe, String> {

    // Lấy tất cả ghế của 1 phòng
    List<Ghe> findByPhongChieu_MaPhong(String maPhong);

    // Check trùng ghế
    boolean existsByPhongChieu_MaPhongAndSoHangAndSoCot(
            String maPhong,
            String soHang,
            Integer soCot
    );
}