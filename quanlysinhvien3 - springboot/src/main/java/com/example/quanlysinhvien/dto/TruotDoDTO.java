package com.example.quanlysinhvien.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TruotDoDTO {
    private String tenMon;
    private double diemQuaTrinh;
    private double diemThanhPhan;
    private double diemCuoiKy;
    private boolean isDo;
}
