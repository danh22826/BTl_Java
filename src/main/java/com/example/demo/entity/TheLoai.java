package com.example.demo.entity;
import jakarta.persistence.*;


@Entity
@Table(name = "TheLoai")
public class TheLoai {

    @Id
    @Column(name = "MaTheLoai")
    private String maTheLoai;

    @Column(name = "TenTheLoai")
    private String tenTheLoai;

    public TheLoai() {
    }

    public TheLoai(String maTheLoai, String tenTheLoai) {
        this.maTheLoai = maTheLoai;
        this.tenTheLoai = tenTheLoai;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }
}