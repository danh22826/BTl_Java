package com.example.demo.controller;

import com.example.demo.entity.Rap;
import com.example.demo.entity.ThanhPho;
import com.example.demo.service.RapService;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RapController {

    private final RapService rapService;

    public RapController(RapService rapService) {
        this.rapService = rapService;
    }



    @GetMapping("/rap")
    public List<Map<String, Object>> getRapTheoThanhPho(@RequestParam String maThanhPho) {
        List<Rap> dsRap = rapService.getRapTheoMaThanhPho(maThanhPho);

        return dsRap.stream().map(r -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("maRap", r.getMaRap());
            map.put("tenRap", r.getTenRap());
            map.put("diaChi", r.getDiaChi());
            return map;
        }).toList();
    }
}