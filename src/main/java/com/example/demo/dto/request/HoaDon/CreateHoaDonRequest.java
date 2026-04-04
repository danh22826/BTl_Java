package com.example.demo.dto.request.HoaDon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateHoaDonRequest {

    @NotBlank(message = "Mã suất chiếu không được để trống")
    private String maSuat;

    @NotBlank(message = "Mã khách hàng không được để trống")
    private String maKhachHang;

    @NotEmpty(message = "Danh sách ghế không được để trống")
    private List<String> dsGhe;

    public String getMaSuat() {
        return maSuat;
    }

    public void setMaSuat(String maSuat) {
        this.maSuat = maSuat;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public List<String> getDsGhe() {
        return dsGhe;
    }

    public void setDsGhe(List<String> dsGhe) {
        this.dsGhe = dsGhe;
    }
}
