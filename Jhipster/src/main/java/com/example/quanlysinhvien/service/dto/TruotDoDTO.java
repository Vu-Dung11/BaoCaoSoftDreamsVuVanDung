package com.example.quanlysinhvien.service.dto;

public class TruotDoDTO {
    private String tenMon;
    private double diemQuaTrinh;
    private double diemThanhPhan;
    private double diemCuoiKy;
    private boolean isDo;

    public TruotDoDTO() {
    }

    public TruotDoDTO(String tenMon, double diemQuaTrinh, double diemThanhPhan, double diemCuoiKy, boolean isDo) {
        this.tenMon = tenMon;
        this.diemQuaTrinh = diemQuaTrinh;
        this.diemThanhPhan = diemThanhPhan;
        this.diemCuoiKy = diemCuoiKy;
        this.isDo = isDo;
    }

    // Getters and Setters
    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public double getDiemQuaTrinh() {
        return diemQuaTrinh;
    }

    public void setDiemQuaTrinh(double diemQuaTrinh) {
        this.diemQuaTrinh = diemQuaTrinh;
    }

    public double getDiemThanhPhan() {
        return diemThanhPhan;
    }

    public void setDiemThanhPhan(double diemThanhPhan) {
        this.diemThanhPhan = diemThanhPhan;
    }

    public double getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(double diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }

    public boolean isDo() {
        return isDo;
    }

    public void setDo(boolean isDo) {
        this.isDo = isDo;
    }
}
