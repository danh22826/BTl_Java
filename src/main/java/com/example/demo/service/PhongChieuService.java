package com.example.demo.service;

import com.example.demo.dto.request.PhongChieu.CreatePhongChieuRequest;
import com.example.demo.dto.request.PhongChieu.UpdatePhongChieuRequest;
import com.example.demo.dto.response.PhongChieuResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhongChieuService {

    private final PhongChieuRepository phongChieuRepository;
    private final RapRepository rapRepository;
    private final LoaiPhongRepository loaiPhongRepository;

    public PhongChieuService(
            PhongChieuRepository phongChieuRepository,
            RapRepository rapRepository,
            LoaiPhongRepository loaiPhongRepository
    ) {
        this.phongChieuRepository = phongChieuRepository;
        this.rapRepository = rapRepository;
        this.loaiPhongRepository = loaiPhongRepository;
    }

    public List<PhongChieuResponse> getByRap(String maRap) {
        return phongChieuRepository.findByRap_MaRap(maRap)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PhongChieuResponse create(CreatePhongChieuRequest request) {

        if (phongChieuRepository.existsByRap_MaRapAndTenPhong(
                request.getMaRap(),
                request.getTenPhong()
        )) {
            throw new RuntimeException("Tên phòng đã tồn tại trong rạp");
        }

        Rap rap = rapRepository.findById(request.getMaRap())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy rạp"));

        LoaiPhong loaiPhong = loaiPhongRepository.findById(request.getMaLoaiPhong())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại phòng"));

        PhongChieu p = new PhongChieu();
        p.setMaPhong(request.getMaPhong());
        p.setTenPhong(request.getTenPhong());
        p.setSucChua(request.getSucChua());
        p.setRap(rap);
        p.setLoaiPhong(loaiPhong);

        return toResponse(phongChieuRepository.save(p));
    }

    public PhongChieuResponse update(String maPhong, UpdatePhongChieuRequest request) {

        PhongChieu p = phongChieuRepository.findById(maPhong)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng"));

        LoaiPhong loaiPhong = loaiPhongRepository.findById(request.getMaLoaiPhong())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại phòng"));

        p.setTenPhong(request.getTenPhong());
        p.setSucChua(request.getSucChua());
        p.setLoaiPhong(loaiPhong);

        return toResponse(phongChieuRepository.save(p));
    }

    public void delete(String maPhong) {
        phongChieuRepository.deleteById(maPhong);
    }

    private PhongChieuResponse toResponse(PhongChieu p) {
        PhongChieuResponse res = new PhongChieuResponse();

        res.setMaPhong(p.getMaPhong());
        res.setTenPhong(p.getTenPhong());
        res.setSucChua(p.getSucChua());

        res.setMaRap(p.getRap().getMaRap());
        res.setTenRap(p.getRap().getTenRap());

        res.setMaLoaiPhong(p.getLoaiPhong().getMaLoaiPhong());
        res.setTenLoaiPhong(p.getLoaiPhong().getTenLoaiPhong());

        return res;
    }
}