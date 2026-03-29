package com.example.demo.controller;

import com.example.demo.dto.request.Ghe.CreateGheRequest;
import com.example.demo.dto.response.GheResponse;
import com.example.demo.service.GheService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ghe")
@CrossOrigin(origins = "*")
public class GheController {

    private final GheService service;

    public GheController(GheService service) {
        this.service = service;
    }

    // Lấy ghế theo phòng
    @GetMapping
    public List<GheResponse> getByPhong(@RequestParam String maPhong) {
        return service.getByPhong(maPhong);
    }

    // Tạo ghế
    @PostMapping
    public GheResponse create(@Valid @RequestBody CreateGheRequest req) {
        return service.create(req);
    }
}