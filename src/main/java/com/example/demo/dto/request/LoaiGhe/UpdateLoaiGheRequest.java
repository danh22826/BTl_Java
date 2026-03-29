package com.example.demo.dto.request.LoaiGhe;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateLoaiGheRequest {

    @NotBlank(message = "Tên loại ghế không được để trống")
    @Size(max = 100, message = "Tên loại ghế tối đa 100 ký tự")
    private String tenLoaiGhe;

    @NotNull(message = "Giá phụ thu không được để trống")
    @Min(value = 0, message = "Giá phụ thu phải >= 0")
    private Double giaPhuThu;

    public UpdateLoaiGheRequest() {
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