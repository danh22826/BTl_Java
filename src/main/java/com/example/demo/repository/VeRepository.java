package com.example.demo.repository;

import com.example.demo.constant.VeStatus;
import com.example.demo.entity.Ve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeRepository extends JpaRepository<Ve, String> {

    List<Ve> findBySuatChieu_MaSuatAndTrangThaiVe(String maSuat, VeStatus status);
}