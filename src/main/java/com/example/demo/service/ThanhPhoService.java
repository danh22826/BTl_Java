// file: ThanhPhoService.java
package com.example.demo.service;

import com.example.demo.entity.ThanhPho;
import com.example.demo.repository.ThanhPhoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThanhPhoService {

    private final ThanhPhoRepository thanhPhoRepository;

    public ThanhPhoService(ThanhPhoRepository thanhPhoRepository) {
        this.thanhPhoRepository = thanhPhoRepository;
    }

    public List<ThanhPho> getAllThanhPho() {
        return thanhPhoRepository.findAll();
    }
}