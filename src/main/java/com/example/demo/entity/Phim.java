package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Phim")
public class Phim {

    @Id
    @Column(name = "MaPhim")
    private String maPhim;

    @Column(name = "TenPhim")
    private String tenPhim;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "Poster")
    private String poster;

    @Column(name = "ThoiLuong")
    private Integer thoiLuong;

    @Column(name = "NgayKhoiChieu")
    private LocalDate ngayKhoiChieu;

    @Column(name = "DoTuoiPhuHop")
    private String doTuoiPhuHop;

    @Column(name = "NgonNgu")
    private String ngonNgu;

    @ManyToMany
    @JoinTable(
            name = "PhimTheLoai", // Bảng trung gian
            joinColumns = @JoinColumn(name = "MaPhim"), // cột mã phim của bảng trung gian -> bảng phim
            inverseJoinColumns = @JoinColumn(name = "MaTheLoai") // cột mã thể loại của bảng trung gina -> bảng thể loại
            // đừng sửa tên biến,
    )
    private List<TheLoai> theLoais; // Danh sách thể loại của 1 phim


    public void setMaPhim(String maPhim) {

        this.maPhim = maPhim;
    }
    public String getMaPhim() {
        return maPhim;
    }
    public void setTenPhim(String tenPhim) {

        this.tenPhim = tenPhim;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public String getMoTa() {

        return moTa;
    }
    public void setMoTa(String moTa) {

        this.moTa = moTa;
    }
    public String getPoster() {

        return poster;
    }
    public void setPoster(String poster) {

        this.poster = poster;
    }
    public Integer getThoiLuong() {

        return thoiLuong;
    }
    public void setThoiLuong(Integer thoiLuong) {

        this.thoiLuong = thoiLuong;
    }
    public LocalDate getNgayKhoiChieu() {

        return ngayKhoiChieu;
    }
    public void setNgayKhoiChieu(LocalDate ngayKhoiChieu) {

        this.ngayKhoiChieu = ngayKhoiChieu;
    }
    public String getDoTuoiPhuHop() {

        return doTuoiPhuHop;
    }
    public void setDoTuoiPhuHop(String doTuoiPhuHop)
    {
        this.doTuoiPhuHop = doTuoiPhuHop;
    }
    public String getNgonNgu() {

        return ngonNgu;
    }
    public void setNgonNgu(String ngonNgu) {

        this.ngonNgu = ngonNgu;
    }

    public List<TheLoai> getTheLoais() {

        return theLoais;
    }
    public void setTheLoais(List<TheLoai> theLoais) {

        this.theLoais = theLoais;
    }
}