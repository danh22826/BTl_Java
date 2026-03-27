package com.example.demo.entity;

import com.example.demo.entity.PhongChieu;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "LoaiPhong")
public class LoaiPhong {

    @Id
    @Column(name = "MaLoaiPhong")
    private String maLoaiPhong;

    @Column(name = "TenLoaiPhong", nullable = false)
    private String tenLoaiPhong;

    @OneToMany(mappedBy = "loaiPhong")
    @JsonIgnore
    private List<PhongChieu> dsPhongChieu;
}