package com.example.demo.DTO;

public class ThanhPhoDTO {

    private String maThanhPho;
    private String tenThanhPho;
    private Long soRap;

    public ThanhPhoDTO(String maThanhPho, String tenThanhPho, Long soRap) {
        this.maThanhPho = maThanhPho;
        this.tenThanhPho = tenThanhPho;
        this.soRap = soRap;
    }

    public String getMaThanhPho() {
        return maThanhPho;
    }

    public void setMaThanhPho(String maThanhPho) {
        this.maThanhPho = maThanhPho;
    }

    public String getTenThanhPho() {
        return tenThanhPho;
    }

    public void setTenThanhPho(String tenThanhPho) {
        this.tenThanhPho = tenThanhPho;
    }

    public Long getSoRap() {
        return soRap;
    }

    public void setSoRap(Long soRap) {
        this.soRap = soRap;
    }
}