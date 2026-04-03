package com.example.demo.repository;

import com.example.demo.constant.HoaDonStatus;
import com.example.demo.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, String> {

    // 🔥 Lấy hóa đơn theo mã khách hàng
    List<HoaDon> findByMaKhachHang(String maKhachHang);

    // 🔥 Lọc theo trạng thái
    List<HoaDon> findByTrangThai(HoaDonStatus trangThai);

    // 🔥 Kết hợp
    List<HoaDon> findByMaKhachHangAndTrangThai(String maKhachHang, HoaDonStatus trangThai);
}