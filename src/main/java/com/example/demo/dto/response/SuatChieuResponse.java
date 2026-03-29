package com.example.demo.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public class SuatChieuResponse {

    private String maSuat;
    private LocalDate ngayChieu;
    private LocalTime gioChieu;
    private Double gia;

    // Trải phẳng thông tin Phim, Phòng, Rạp
    private String maPhim;
    private String tenPhim;

    private String maPhong;
    private String tenPhong;

    private String maRap;
    private String tenRap;

    public SuatChieuResponse() {
    }

    public String getMaSuat() { return maSuat; }
    public void setMaSuat(String maSuat) { this.maSuat = maSuat; }

    public LocalDate getNgayChieu() { return ngayChieu; }
    public void setNgayChieu(LocalDate ngayChieu) { this.ngayChieu = ngayChieu; }

    public LocalTime getGioChieu() { return gioChieu; }
    public void setGioChieu(LocalTime gioChieu) { this.gioChieu = gioChieu; }

    public Double getGia() { return gia; }
    public void setGia(Double gia) { this.gia = gia; }

    public String getMaPhim() { return maPhim; }
    public void setMaPhim(String maPhim) { this.maPhim = maPhim; }

    public String getTenPhim() { return tenPhim; }
    public void setTenPhim(String tenPhim) { this.tenPhim = tenPhim; }

    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }

    public String getTenPhong() { return tenPhong; }
    public void setTenPhong(String tenPhong) { this.tenPhong = tenPhong; }

    public String getMaRap() { return maRap; }
    public void setMaRap(String maRap) { this.maRap = maRap; }

    public String getTenRap() { return tenRap; }
    public void setTenRap(String tenRap) { this.tenRap = tenRap; }
}