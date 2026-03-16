package com.example.demo.service;

import com.example.demo.entity.Phim;
import com.example.demo.repository.PhimRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PhimService {

    private final PhimRepository phimRepository;

    public PhimService(PhimRepository phimRepository) {
        this.phimRepository = phimRepository;
    }

    public List<Phim> getAllPhim() {
        return phimRepository.findAll();
    }

    public Phim getPhimById(String id) {
        return phimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phim: " + id));
    }

    public Phim createPhim(Phim phim) {
        return phimRepository.save(phim);
    }

    public Phim updatePhim(String id, Phim details) {
        Phim phim = getPhimById(id);
        phim.setTenPhim(details.getTenPhim());
        phim.setMoTa(details.getMoTa());
        phim.setPoster(details.getPoster());
        phim.setThoiLuong(details.getThoiLuong());
        phim.setNgayKhoiChieu(details.getNgayKhoiChieu());
        phim.setDoTuoiPhuHop(details.getDoTuoiPhuHop());
        phim.setNgonNgu(details.getNgonNgu());
        return phimRepository.save(phim);
    }

    public void deletePhim(String id) {
        phimRepository.deleteById(id);
    }
}