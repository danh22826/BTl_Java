package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "SuatChieu")
public class SuatChieu {

    @Id
    @Column(name = "MaSuatChieu", length = 20, nullable = false)
    private String maSuatChieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPhong")
    @JsonIgnoreProperties({"suatChieus"})
    private PhongChieu phongChieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPhim")
    private Phim phim;

    @Column(name = "ThoiGianChieu")
    private LocalTime thoiGianChieu;

    @Column(name = "NgayChieu")
    private LocalDate ngayChieu;

    public SuatChieu() {
    }

    public String getMaSuatChieu() {
        return maSuatChieu;
    }

    public void setMaSuatChieu(String maSuatChieu) {
        this.maSuatChieu = maSuatChieu;
    }

    public PhongChieu getPhongChieu() {
        return phongChieu;
    }

    public void setPhongChieu(PhongChieu phongChieu) {
        this.phongChieu = phongChieu;
    }

    public Phim getPhim() {
        return phim;
    }

    public void setPhim(Phim phim) {
        this.phim = phim;
    }

    public LocalTime getThoiGianChieu() {
        return thoiGianChieu;
    }

    public void setThoiGianChieu(LocalTime thoiGianChieu) {
        this.thoiGianChieu = thoiGianChieu;
    }

    public LocalDate getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(LocalDate ngayChieu) {
        this.ngayChieu = ngayChieu;
    }
}

