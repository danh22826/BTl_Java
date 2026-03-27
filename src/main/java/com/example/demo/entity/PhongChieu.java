package com.example.demo.entity;

import com.example.demo.entity.Ghe;
import com.example.demo.entity.LoaiPhong;
import com.example.demo.entity.Rap;
import com.example.demo.entity.SuatChieu;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "PhongChieu",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_rap_tenphong", columnNames = {"MaRap", "TenPhong"})
        }
)
public class PhongChieu {

    @Id
    @Column(name = "MaPhong")
    private String maPhong;

    @Column(name = "TenPhong", nullable = false)
    private String tenPhong;

    @Column(name = "SucChua", nullable = false)
    private Integer sucChua;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaRap", nullable = false)
    @JsonIgnoreProperties("phongChieus")
    private Rap rap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaLoaiPhong", nullable = false)
    @JsonIgnoreProperties("dsPhongChieu")
    private LoaiPhong loaiPhong;

    @OneToMany(mappedBy = "phongChieu")
    @JsonIgnore
    private List<Ghe> ghes;

    @OneToMany(mappedBy = "phongChieu")
    @JsonIgnore
    private List<SuatChieu> suatChieus;

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public Integer getSucChua() {
        return sucChua;
    }

    public void setSucChua(Integer sucChua) {
        this.sucChua = sucChua;
    }

    public Rap getRap() {
        return rap;
    }

    public void setRap(Rap rap) {
        this.rap = rap;
    }

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public List<Ghe> getGhes() {
        return ghes;
    }

    public void setGhes(List<Ghe> ghes) {
        this.ghes = ghes;
    }

    public List<SuatChieu> getSuatChieus() {
        return suatChieus;
    }

    public void setSuatChieus(List<SuatChieu> suatChieus) {
        this.suatChieus = suatChieus;
    }
}