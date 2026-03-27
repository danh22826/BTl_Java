package com.example.demo.service;

import com.example.demo.entity.Rap;
import com.example.demo.entity.ThanhPho;
import com.example.demo.repository.RapRepository;
import com.example.demo.repository.ThanhPhoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RapService {

    private final RapRepository rapRepository;
    private final ThanhPhoRepository thanhPhoRepository;

    public RapService(RapRepository rapRepository, ThanhPhoRepository thanhPhoRepository) {
        this.rapRepository = rapRepository;
        this.thanhPhoRepository = thanhPhoRepository;
    }

    public List<ThanhPho> getAllThanhPho() {
        return thanhPhoRepository.findAll();
    }

    public List<Rap> getRapTheoMaThanhPho(String maThanhPho) {
        return rapRepository.findByThanhPho_MaThanhPhoOrderByTenRapAsc(maThanhPho);
    }
}