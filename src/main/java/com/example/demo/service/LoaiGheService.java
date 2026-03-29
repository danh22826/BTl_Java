package com.example.demo.service;

import com.example.demo.entity.LoaiGhe;
import com.example.demo.repository.LoaiGheRepository;
import org.springframework.stereotype.Service;
import com.example.demo.dto.response.LoaiGheResponse;
import com.example.demo.dto.request.LoaiGhe.CreateLoaiGheRequest;
import com.example.demo.dto.request.LoaiGhe.UpdateLoaiGheRequest;
import java.util.List;

@Service
public class LoaiGheService {

    private final LoaiGheRepository loaiGheRepository;

    public LoaiGheService(LoaiGheRepository loaiGheRepository) {
        this.loaiGheRepository = loaiGheRepository;
    }

    public List<LoaiGheResponse> getAllLoaiGhe() {
        return loaiGheRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public LoaiGheResponse getLoaiGheById(String maLoaiGhe) {
        LoaiGhe loaiGhe = loaiGheRepository.findById(maLoaiGhe)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại ghế với mã: " + maLoaiGhe));

        return toResponse(loaiGhe);
    }

    public LoaiGheResponse createLoaiGhe(CreateLoaiGheRequest request) {
        if (loaiGheRepository.existsById(request.getMaLoaiGhe())) {
            throw new RuntimeException("Mã loại ghế đã tồn tại");
        }

        if (loaiGheRepository.existsByTenLoaiGhe(request.getTenLoaiGhe())) {
            throw new RuntimeException("Tên loại ghế đã tồn tại");
        }

        LoaiGhe loaiGhe = new LoaiGhe();
        loaiGhe.setMaLoaiGhe(request.getMaLoaiGhe());
        loaiGhe.setTenLoaiGhe(request.getTenLoaiGhe());
        loaiGhe.setGiaPhuThu(request.getGiaPhuThu());

        return toResponse(loaiGheRepository.save(loaiGhe));
    }

    public LoaiGheResponse updateLoaiGhe(String maLoaiGhe, UpdateLoaiGheRequest request) {
        LoaiGhe loaiGhe = loaiGheRepository.findById(maLoaiGhe)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại ghế với mã: " + maLoaiGhe));

        loaiGhe.setTenLoaiGhe(request.getTenLoaiGhe());
        loaiGhe.setGiaPhuThu(request.getGiaPhuThu());

        return toResponse(loaiGheRepository.save(loaiGhe));
    }

    public void deleteLoaiGhe(String maLoaiGhe) {
        if (!loaiGheRepository.existsById(maLoaiGhe)) {
            throw new RuntimeException("Không tìm thấy loại ghế với mã: " + maLoaiGhe);
        }

        loaiGheRepository.deleteById(maLoaiGhe);
    }

    private LoaiGheResponse toResponse(LoaiGhe loaiGhe) {
        LoaiGheResponse response = new LoaiGheResponse();
        response.setMaLoaiGhe(loaiGhe.getMaLoaiGhe());
        response.setTenLoaiGhe(loaiGhe.getTenLoaiGhe());
        response.setGiaPhuThu(loaiGhe.getGiaPhuThu());
        return response;
    }
}