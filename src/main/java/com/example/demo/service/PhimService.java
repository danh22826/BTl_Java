package com.example.demo.service;

import com.example.demo.dto.request.Phim.*;
import com.example.demo.dto.response.PhimResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PhimService {

    private final PhimRepository phimRepo;
    private final TheLoaiRepository theLoaiRepo;

    public PhimService(PhimRepository phimRepo, TheLoaiRepository theLoaiRepo) {
        this.phimRepo = phimRepo;
        this.theLoaiRepo = theLoaiRepo;
    }

    public List<PhimResponse> getAll() {
        return phimRepo.findAll().stream().map(this::toResponse).toList();
    }

    public PhimResponse getById(String id) {
        Phim p = phimRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phim"));
        return toResponse(p);
    }

    public PhimResponse create(CreatePhimRequest req) {

        if (phimRepo.existsById(req.getMaPhim())) {
            throw new RuntimeException("Trùng mã phim");
        }

        Phim p = new Phim();
        p.setMaPhim(req.getMaPhim());
        p.setTenPhim(req.getTenPhim());
        p.setMoTa(req.getMoTa());
        p.setPoster(req.getPoster());
        p.setThoiLuong(req.getThoiLuong());
        p.setNgayKhoiChieu(req.getNgayKhoiChieu());
        p.setDoTuoiPhuHop(req.getDoTuoiPhuHop());
        p.setNgonNgu(req.getNgonNgu());

        if (req.getMaTheLoais() != null) {
            List<TheLoai> list = theLoaiRepo.findByMaTheLoaiIn(req.getMaTheLoais());
            p.setTheLoais(list);
        } else {
            p.setTheLoais(Collections.emptyList());
        }

        return toResponse(phimRepo.save(p));
    }

    public PhimResponse update(String id, UpdatePhimRequest req) {

        Phim p = phimRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phim"));

        p.setTenPhim(req.getTenPhim());
        p.setMoTa(req.getMoTa());
        p.setPoster(req.getPoster());
        p.setThoiLuong(req.getThoiLuong());
        p.setNgayKhoiChieu(req.getNgayKhoiChieu());
        p.setDoTuoiPhuHop(req.getDoTuoiPhuHop());
        p.setNgonNgu(req.getNgonNgu());

        if (req.getMaTheLoais() != null) {
            List<TheLoai> list = theLoaiRepo.findByMaTheLoaiIn(req.getMaTheLoais());
            p.setTheLoais(list);
        }

        return toResponse(phimRepo.save(p));
    }

    public void delete(String id) {
        phimRepo.deleteById(id);
    }

    private PhimResponse toResponse(Phim p) {

        PhimResponse r = new PhimResponse();

        r.setMaPhim(p.getMaPhim());
        r.setTenPhim(p.getTenPhim());
        r.setMoTa(p.getMoTa());
        r.setPoster(p.getPoster());
        r.setThoiLuong(p.getThoiLuong());
        r.setNgayKhoiChieu(p.getNgayKhoiChieu());
        r.setDoTuoiPhuHop(p.getDoTuoiPhuHop());
        r.setNgonNgu(p.getNgonNgu());

        if (p.getTheLoais() != null) {
            r.setTheLoais(
                    p.getTheLoais().stream()
                            .map(TheLoai::getTenTheLoai)
                            .toList()
            );
        }

        return r;
    }
}