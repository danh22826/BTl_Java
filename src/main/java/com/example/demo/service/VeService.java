package com.example.demo.service;

import com.example.demo.constant.VeStatus;
import com.example.demo.entity.Ve;
import com.example.demo.repository.VeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VeService {

    private final VeRepository veRepository;

    public VeService(VeRepository veRepository) {
        this.veRepository = veRepository;
    }

    // Lấy danh sách ghế còn trống của 1 suất chiếu
    public List<Ve> getGheTrong(String maSuat) {
        return veRepository.findBySuatChieu_MaSuatAndTrangThaiVe(maSuat, VeStatus.TRONG);
    }

    // Đặt vé
    public Ve datVe(Ve ve) {
        ve.setTrangThaiVe(VeStatus.DA_DAT);
        ve.setNgayDat(LocalDateTime.now());
        return veRepository.save(ve);
    }

    // Thanh toán vé
    public Ve thanhToanVe(String maVe) {
        Ve ve = veRepository.findById(maVe).orElseThrow();
        ve.setTrangThaiVe(VeStatus.DA_THANH_TOAN);
        return veRepository.save(ve);
    }

    // Hủy vé
    public Ve huyVe(String maVe) {
        Ve ve = veRepository.findById(maVe).orElseThrow();
        ve.setTrangThaiVe(VeStatus.DA_HUY);
        return veRepository.save(ve);
    }
}