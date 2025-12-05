package com.mycompany.qlsvsqlv1;

public class KetQuaHocTap {
    private MonHoc monHoc;
    private double diemThanhPhan;
    private double diemQuaTrinh;

    public KetQuaHocTap(MonHoc monHoc) {
        this.monHoc = monHoc;
        this.diemThanhPhan = 0;
        this.diemQuaTrinh = 0;
    }

    public void nhapDiem(double diemQT, double diemTP) {
        this.diemQuaTrinh = diemQT;
        this.diemThanhPhan = diemTP;
    }

    public double tinhDiemTongKet() {
        var diemTongKet = (diemQuaTrinh * monHoc.getTyLeDiemQuaTrinh()) + (diemThanhPhan * monHoc.getTyLeDiemThanhPhan());
        return diemTongKet;
    }


    public boolean isQuaMon() {
        return tinhDiemTongKet() > 4.0;
    }


    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    public Double getDiemThanhPhan() {
        return diemThanhPhan;
    }

    public void setDiemThanhPhan(Double diemThanhPhan) {
        this.diemThanhPhan = diemThanhPhan;
    }

    public Double getDiemQuaTrinh() {
        return diemQuaTrinh;
    }

    public void setDiemQuaTrinh(Double diemQuaTrinh) {
        this.diemQuaTrinh = diemQuaTrinh;
    }

    @Override
    public String toString() {
        return "KetQuaHocTap{" + "monHoc=" + monHoc.getTenMon() + ", diemThanhPhan=" + diemThanhPhan + ", diemQuaTrinh=" + diemQuaTrinh + '}';
    }
}
