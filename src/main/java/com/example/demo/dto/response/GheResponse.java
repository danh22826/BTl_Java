package com.example.demo.dto.response;

public class GheResponse {

    private String maGhe;
    private String tenGhe;

    private String maPhong;

    private String maLoaiGhe;
    private String tenLoaiGhe;
    private Double giaPhuThu;

    public GheResponse() {
    }

    public String getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(String maGhe) {
        this.maGhe = maGhe;
    }

    public String getTenGhe() {
        return tenGhe;
    }

    public void setTenGhe(String tenGhe) {
        this.tenGhe = tenGhe;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
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
}