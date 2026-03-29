package com.example.demo.service;

import com.example.demo.dto.request.Ghe.CreateGheRequest;
import com.example.demo.dto.response.GheResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GheService {

    private final GheRepository gheRepo;
    private final PhongChieuRepository phongRepo;
    private final LoaiGheRepository loaiRepo;

    public GheService(GheRepository gheRepo,
                      PhongChieuRepository phongRepo,
                      LoaiGheRepository loaiRepo) {
        this.gheRepo = gheRepo;
        this.phongRepo = phongRepo;
        this.loaiRepo = loaiRepo;
    }

    // Lấy danh sách ghế theo phòng
    public List<GheResponse> getByPhong(String maPhong) {
        return gheRepo.findByPhongChieu_MaPhong(maPhong)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Tạo ghế
    public GheResponse create(CreateGheRequest req) {

        if (gheRepo.existsByPhongChieu_MaPhongAndSoHangAndSoCot(
                req.getMaPhong(),
                req.getSoHang(),
                req.getSoCot()
        )) {
            throw new RuntimeException("Ghế đã tồn tại trong phòng");
        }

        PhongChieu phong = phongRepo.findById(req.getMaPhong())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng"));

        LoaiGhe loaiGhe = loaiRepo.findById(req.getMaLoaiGhe())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại ghế"));

        Ghe ghe = new Ghe();
        ghe.setMaGhe(req.getMaGhe());
        ghe.setPhongChieu(phong);
        ghe.setSoHang(req.getSoHang());
        ghe.setSoCot(req.getSoCot());
        ghe.setLoaiGhe(loaiGhe);

        return toResponse(gheRepo.save(ghe));
    }

    // Map entity -> DTO
    private GheResponse toResponse(Ghe g) {

        GheResponse r = new GheResponse();

        r.setMaGhe(g.getMaGhe());
        r.setTenGhe(g.getTenGhe()); // A10

        r.setMaPhong(g.getPhongChieu().getMaPhong());

        r.setMaLoaiGhe(g.getLoaiGhe().getMaLoaiGhe());
        r.setTenLoaiGhe(g.getLoaiGhe().getTenLoaiGhe());
        r.setGiaPhuThu(g.getLoaiGhe().getGiaPhuThu());

        return r;
    }
}