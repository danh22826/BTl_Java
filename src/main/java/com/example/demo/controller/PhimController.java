package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.PhimResponse;
import com.example.demo.dto.request.Phim.CreatePhimRequest;
import com.example.demo.dto.request.Phim.UpdatePhimRequest;
import com.example.demo.service.PhimService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phim")
@CrossOrigin(origins = "*")
public class PhimController {

    private final PhimService service;

    public PhimController(PhimService service) {
        this.service = service;
    }

    @GetMapping
    public List<PhimResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PhimResponse getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public PhimResponse create(@Valid @RequestBody CreatePhimRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public PhimResponse update(@PathVariable String id,
                               @Valid @RequestBody UpdatePhimRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}