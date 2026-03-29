package com.example.demo.dto.response;

public class LoaiGheResponse {

    private String maLoaiGhe;
    private String tenLoaiGhe;
    private Double giaPhuThu;

    public LoaiGheResponse() {
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