package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Rap")
public class Rap {

    @Id
    @Column(name = "MaRap")
    private String maRap;

    @Column(name = "TenRap")
    private String tenRap;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "MaThanhPho")
    private String maThanhPho;

    @OneToMany(mappedBy = "rap")
    @JsonIgnore
    private List<PhongChieu> phongChieus;

    public String getMaRap() {
        return maRap;
    }

    public void setMaRap(String maRap) {
        this.maRap = maRap;
    }

    public String getTenRap() {
        return tenRap;
    }

    public void setTenRap(String tenRap) {
        this.tenRap = tenRap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaThanhPho() {
        return maThanhPho;
    }

    public void setMaThanhPho(String maThanhPho) {
        this.maThanhPho = maThanhPho;
    }

    public List<PhongChieu> getPhongChieus() {
        return phongChieus;
    }

    public void setPhongChieus(List<PhongChieu> phongChieus) {
        this.phongChieus = phongChieus;
    }
}