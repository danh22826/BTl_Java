package com.example.demo.dto.request.PhongChieu;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePhongChieuRequest {

    @NotBlank(message = "Mã phòng không được để trống")
    @Size(max = 20, message = "Mã phòng không được vượt quá 20 ký tự")
    private String maPhong;

    @NotBlank(message = "Tên phòng không được để trống")
    @Size(max = 100, message = "Tên phòng không được vượt quá 100 ký tự")
    private String tenPhong;

    @NotNull(message = "Sức chứa không được để trống")
    @Min(value = 1, message = "Sức chứa phải từ 1 ghế trở lên")
    private Integer sucChua;

    @NotBlank(message = "Mã rạp không được để trống")
    private String maRap;

    @NotBlank(message = "Mã loại phòng không được để trống")
    private String maLoaiPhong;

    public CreatePhongChieuRequest() {
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public Integer getSucChua() {
        return sucChua;
    }

    public void setSucChua(Integer sucChua) {
        this.sucChua = sucChua;
    }

    public String getMaRap() {
        return maRap;
    }

    public void setMaRap(String maRap) {
        this.maRap = maRap;
    }

    public String getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }
}