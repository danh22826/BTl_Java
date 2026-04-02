package com.example.demo.service;

import com.example.demo.dto.request.PhongChieu.CreatePhongChieuRequest;
import com.example.demo.dto.request.PhongChieu.UpdatePhongChieuRequest;
import com.example.demo.dto.response.PhongChieuResponse;
import com.example.demo.entity.LoaiPhong;
import com.example.demo.entity.PhongChieu;
import com.example.demo.entity.Rap;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.LoaiPhongRepository;
import com.example.demo.repository.PhongChieuRepository;
import com.example.demo.repository.RapRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
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

    @Transactional
    public PhongChieuResponse create(CreatePhongChieuRequest request) {

        if (phongChieuRepository.existsByRap_MaRapAndTenPhong(
                request.getMaRap(),
                request.getTenPhong()
        )) {
            throw new ConflictException("Tên phòng đã tồn tại trong rạp");
        }

        Rap rap = rapRepository.findById(request.getMaRap())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy rạp"));

        LoaiPhong loaiPhong = loaiPhongRepository.findById(request.getMaLoaiPhong())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy loại phòng"));

        PhongChieu p = new PhongChieu();
        p.setMaPhong(request.getMaPhong());
        p.setTenPhong(request.getTenPhong());
        p.setSucChua(request.getSucChua());
        p.setRap(rap);
        p.setLoaiPhong(loaiPhong);

        try {
            return toResponse(phongChieuRepository.save(p));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Phòng chiếu đã tồn tại hoặc vi phạm ràng buộc dữ liệu");
        }
    }

    @Transactional
    public PhongChieuResponse update(String maPhong, UpdatePhongChieuRequest request) {

        PhongChieu p = phongChieuRepository.findById(maPhong)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy phòng"));

        LoaiPhong loaiPhong = loaiPhongRepository.findById(request.getMaLoaiPhong())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy loại phòng"));

        p.setTenPhong(request.getTenPhong());
        p.setSucChua(request.getSucChua());
        p.setLoaiPhong(loaiPhong);

        try {
            return toResponse(phongChieuRepository.save(p));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Phòng chiếu đã tồn tại hoặc vi phạm ràng buộc dữ liệu");
        }
    }

    @Transactional
    public void delete(String maPhong) {
        if (!phongChieuRepository.existsById(maPhong)) {
            throw new NotFoundException("Không tìm thấy phòng");
        }
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