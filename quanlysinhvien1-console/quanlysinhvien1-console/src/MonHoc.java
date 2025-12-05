public class MonHoc {

    private String tenMon;
    private Double tyLeDiemQuaTrinh;
    // Ví dụ: 0.3 (30%)

    public MonHoc() {
    }

    public MonHoc(String tenMon, Double tyLeDiemQuaTrinh) {
        this.tenMon = tenMon;
        this.tyLeDiemQuaTrinh = tyLeDiemQuaTrinh;
    }


    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public void setTyLeDiemQuaTrinh(Double tyLeDiemQuaTrinh) {
        this.tyLeDiemQuaTrinh = tyLeDiemQuaTrinh;
    }

    public Double getTyLeDiemQuaTrinh() {
        return tyLeDiemQuaTrinh;
    }

    public double getTyLeDiemThanhPhan() {
        return 1.0 - tyLeDiemQuaTrinh;
    }
}