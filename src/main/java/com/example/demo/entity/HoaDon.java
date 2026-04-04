package com.example.demo.entity;

import com.example.demo.constant.HoaDonStatus;
import com.example.demo.converter.HoaDonStatusConverter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "HoaDon")
public class HoaDon {

    @Id
    @Column(name = "MaDon")
    private String maDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKhachHang", nullable = false)
    private KhachHang khachHang;

    @Column(name = "TongTien")
    private BigDecimal tongTien;

    @Column(name = "ThoiGianDat")
    private LocalDateTime thoiGianDat;

    @Column(name = "ThoiGianThanhToan")
    private LocalDateTime thoiGianThanhToan;

    @Convert(converter = HoaDonStatusConverter.class)
    @Column(name = "TrangThai")
    private HoaDonStatus trangThai;

    @Column(name = "PhuongThucThanhToan")
    private String phuongThucThanhToan;

    public HoaDon() {
    }

    public String getMaDon() {
        return maDon;
    }

    public void setMaDon(String maDon) {
        this.maDon = maDon;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public LocalDateTime getThoiGianDat() {
        return thoiGianDat;
    }

    public void setThoiGianDat(LocalDateTime thoiGianDat) {
        this.thoiGianDat = thoiGianDat;
    }

    public LocalDateTime getThoiGianThanhToan() {
        return thoiGianThanhToan;
    }

    public void setThoiGianThanhToan(LocalDateTime thoiGianThanhToan) {
        this.thoiGianThanhToan = thoiGianThanhToan;
    }

    public HoaDonStatus getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(HoaDonStatus trangThai) {
        this.trangThai = trangThai;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }
}
