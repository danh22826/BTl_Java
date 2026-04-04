package com.example.demo.controller;

import com.example.demo.dto.request.HoaDon.CreateHoaDonRequest;
import com.example.demo.dto.request.HoaDon.ThanhToanHoaDonRequest;
import com.example.demo.dto.response.HoaDonDetailResponse;
import com.example.demo.service.HoaDonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hoa-don")
@CrossOrigin(origins = "*")
public class HoaDonController {

    private final HoaDonService hoaDonService;

    public HoaDonController(HoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HoaDonDetailResponse create(@Valid @RequestBody CreateHoaDonRequest request) {
        return hoaDonService.createPendingInvoice(request);
    }

    @GetMapping("/{maDon}")
    public HoaDonDetailResponse getById(@PathVariable String maDon) {
        return hoaDonService.getDetail(maDon);
    }

    @PostMapping("/{maDon}/thanh-toan")
    public HoaDonDetailResponse pay(@PathVariable String maDon,
                                    @Valid @RequestBody ThanhToanHoaDonRequest request) {
        return hoaDonService.pay(maDon, request.getPhuongThucThanhToan());
    }

    @PostMapping("/{maDon}/huy")
    public HoaDonDetailResponse cancel(@PathVariable String maDon) {
        return hoaDonService.cancel(maDon);
    }
}
