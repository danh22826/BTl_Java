package com.example.demo.service;

import com.example.demo.dto.request.SuatChieu.CreateSuatChieuRequest;
import com.example.demo.dto.request.SuatChieu.UpdateSuatChieuRequest;
import com.example.demo.dto.response.SuatChieuResponse;
import com.example.demo.entity.Phim;
import com.example.demo.entity.PhongChieu;
import com.example.demo.entity.SuatChieu;
import com.example.demo.repository.PhimRepository;
import com.example.demo.repository.PhongChieuRepository;
import com.example.demo.repository.SuatChieuRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class SuatChieuService {

    private final SuatChieuRepository suatChieuRepository;
    private final PhimRepository phimRepository;
    private final PhongChieuRepository phongChieuRepository;

    public SuatChieuService(SuatChieuRepository suatChieuRepository,
                            PhimRepository phimRepository,
                            PhongChieuRepository phongChieuRepository) {
        this.suatChieuRepository = suatChieuRepository;
        this.phimRepository = phimRepository;
        this.phongChieuRepository = phongChieuRepository;
    }

    public List<Map<String, Object>> getPhimTheoNgayVaRap(String maRap, String ngay) {
        LocalDate localDate = LocalDate.parse(ngay);
        return suatChieuRepository.timPhimTheoNgayVaRap(maRap, localDate);
    }

    public List<Map<String, Object>> getSuatChieuConHan(String maPhim, String maRap, String ngay) {
        LocalDate localDate = LocalDate.parse(ngay);
        return suatChieuRepository.timSuatChieuConHan(maPhim, maRap, localDate);
    }

    public List<SuatChieuResponse> getAll() {
        return suatChieuRepository.findAll().stream().map(this::toResponse).toList();
    }

    public SuatChieuResponse getById(String id) {
        SuatChieu sc = suatChieuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy suất chiếu"));
        return toResponse(sc);
    }

    public SuatChieuResponse create(CreateSuatChieuRequest req) {
        if (suatChieuRepository.existsById(req.getMaSuat())) {
            throw new RuntimeException("Mã suất chiếu đã tồn tại");
        }

        Phim phim = phimRepository.findById(req.getMaPhim())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phim"));

        PhongChieu phong = phongChieuRepository.findById(req.getMaPhong())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng chiếu"));

        SuatChieu sc = new SuatChieu();
        sc.setMaSuat(req.getMaSuat());
        sc.setPhim(phim);
        sc.setPhongChieu(phong);
        sc.setNgayChieu(req.getNgayChieu());
        sc.setGioChieu(req.getGioChieu());
        sc.setGia(req.getGia());

        return toResponse(suatChieuRepository.save(sc));
    }

    public SuatChieuResponse update(String id, UpdateSuatChieuRequest req) {
        SuatChieu sc = suatChieuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy suất chiếu"));

        Phim phim = phimRepository.findById(req.getMaPhim())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phim"));

        PhongChieu phong = phongChieuRepository.findById(req.getMaPhong())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng chiếu"));

        sc.setPhim(phim);
        sc.setPhongChieu(phong);
        sc.setNgayChieu(req.getNgayChieu());
        sc.setGioChieu(req.getGioChieu());
        sc.setGia(req.getGia());

        return toResponse(suatChieuRepository.save(sc));
    }

    public void delete(String id) {
        suatChieuRepository.deleteById(id);
    }

    private SuatChieuResponse toResponse(SuatChieu sc) {
        SuatChieuResponse res = new SuatChieuResponse();

        res.setMaSuat(sc.getMaSuat());
        res.setNgayChieu(sc.getNgayChieu());
        res.setGioChieu(sc.getGioChieu());
        res.setGia(sc.getGia());

        res.setMaPhim(sc.getPhim().getMaPhim());
        res.setTenPhim(sc.getPhim().getTenPhim());

        res.setMaPhong(sc.getPhongChieu().getMaPhong());
        res.setTenPhong(sc.getPhongChieu().getTenPhong());

        res.setMaRap(sc.getPhongChieu().getRap().getMaRap());
        res.setTenRap(sc.getPhongChieu().getRap().getTenRap());

        return res;
    }
}