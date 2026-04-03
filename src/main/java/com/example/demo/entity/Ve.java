package com.example.demo.entity;

import com.example.demo.constant.VeStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Ve")
public class Ve {

    @Id
    @Column(name = "MaVe")
    private String maVe;

    @ManyToOne
    @JoinColumn(name = "MaSuat", nullable = false)
    private SuatChieu suatChieu;

    @ManyToOne
    @JoinColumn(name = "MaGhe", nullable = false)
    private Ghe ghe;

    @ManyToOne
    @JoinColumn(name = "MaDon")
    private HoaDon hoaDon;

    @Enumerated(EnumType.STRING) // map enum sang string trong DB
    @Column(name = "TrangThaiVe", nullable = false)
    private VeStatus trangThaiVe;

    @Column(name = "NgayDat")
    private LocalDateTime ngayDat;

    
    // getter và setter
    public String getMaVe() { return maVe; }
    public void setMaVe(String maVe) { this.maVe = maVe; }

    public SuatChieu getSuatChieu() { return suatChieu; }
    public void setSuatChieu(SuatChieu suatChieu) { this.suatChieu = suatChieu; }

    public Ghe getGhe() { return ghe; }
    public void setGhe(Ghe ghe) { this.ghe = ghe; }

    public HoaDon getHoaDon() { return hoaDon; }
    public void setHoaDon(HoaDon hoaDon) { this.hoaDon = hoaDon; }

    public VeStatus getTrangThaiVe() { return trangThaiVe; }
    public void setTrangThaiVe(VeStatus trangThaiVe) { this.trangThaiVe = trangThaiVe; }

    public LocalDateTime getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDateTime ngayDat) { this.ngayDat = ngayDat; }
}