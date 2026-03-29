package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "LoaiGhe")
public class LoaiGhe {

    @Id
    @Column(name = "MaLoaiGhe")
    private String maLoaiGhe;

    @Column(name = "TenLoaiGhe")
    private String tenLoaiGhe;

    @Column(name = "GiaPhuThu")
    private Double giaPhuThu;

    @OneToMany(mappedBy = "loaiGhe")
    @JsonIgnore
    private List<Ghe> dsGhe;

    public LoaiGhe() {
    }

    public String getMaLoaiGhe() {
        return maLoaiGhe;
    }

    public void setMaLoaiGhe(String maLoaiGhe) {
        this.maLoaiGhe = maLoaiGhe;
    }

    public String getTenLoaiGhe() {
        return tenLoaiGhe;
    }

    public void setTenLoaiGhe(String tenLoaiGhe) {
        this.tenLoaiGhe = tenLoaiGhe;
    }

    public Double getGiaPhuThu() {
        return giaPhuThu;
    }

    public void setGiaPhuThu(Double giaPhuThu) {
        this.giaPhuThu = giaPhuThu;
    }

    public List<Ghe> getDsGhe() {
        return dsGhe;
    }

    public void setDsGhe(List<Ghe> dsGhe) {
        this.dsGhe = dsGhe;
    }
}