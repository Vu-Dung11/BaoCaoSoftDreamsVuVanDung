package com.mycompany.qlsvsqlv1;

public class MonHoc {
    private int maMon;
    private String tenMon;
    private Double tyLeDiemQuaTrinh;
    // Ví dụ: 0.3 (30%)

    public MonHoc() {
    }

    public MonHoc(int maMon, String tenMon, Double tyLeDiemQuaTrinh) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.tyLeDiemQuaTrinh = tyLeDiemQuaTrinh;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public Double getTyLeDiemQuaTrinh() {
        return tyLeDiemQuaTrinh;
    }


    public double getTyLeDiemThanhPhan() {
        return 1.0 - tyLeDiemQuaTrinh;
    }
}