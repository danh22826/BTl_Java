package com.example.demo.service;

import com.example.demo.constant.HoaDonStatus;
import com.example.demo.entity.HoaDon;
import com.example.demo.repository.HoaDonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HoaDonService {

    private final HoaDonRepository hoaDonRepository;

    public HoaDonService(HoaDonRepository hoaDonRepository) {
        this.hoaDonRepository = hoaDonRepository;
    }

    public HoaDon taoHoaDon(HoaDon hoaDon) {
        hoaDon.setThoiGianDat(LocalDateTime.now());
        hoaDon.setTrangThai(HoaDonStatus.CHUA_THANH_TOAN);
        return hoaDonRepository.save(hoaDon);
    }

    public HoaDon thanhToanHoaDon(String maDon) {
        HoaDon hd = hoaDonRepository.findById(maDon)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn: " + maDon));

        hd.setTrangThai(HoaDonStatus.DA_THANH_TOAN);
        hd.setThoiGianThanhToan(LocalDateTime.now());

        return hoaDonRepository.save(hd);
    }

    public HoaDon huyHoaDon(String maDon) {
        HoaDon hd = hoaDonRepository.findById(maDon)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn: " + maDon));

        hd.setTrangThai(HoaDonStatus.DA_HUY);

        return hoaDonRepository.save(hd);
    }
}