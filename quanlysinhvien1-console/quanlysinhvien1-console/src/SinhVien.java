import java.util.List;

public class SinhVien {
    private Long maSV;
    private String tenSV;
    private String gioiTinh;
    private String ngaySinh;
    private String lop;
    private String khoa;
    private List<KetQuaHocTap> bangDiem;


    public SinhVien() {
    }
    public void dangKyMonHoc(MonHoc mon) {
        bangDiem.add(new KetQuaHocTap(mon));
    }

    public SinhVien(Long maSV, String tenSV, String gioiTinh, String ngaySinh, String lop, String khoa, List<KetQuaHocTap> bangDiem) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.lop = lop;
        this.khoa = khoa;
        this.bangDiem = bangDiem;
    }

    public Long getMaSV() {
        return maSV;
    }

    public void setMaSV(Long maSV) {
        this.maSV = maSV;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public List<KetQuaHocTap> getBangDiem() {
        return bangDiem;
    }

    public void setBangDiem(List<KetQuaHocTap> bangDiem) {
        this.bangDiem = bangDiem;
    }
    @Override
    public String toString() {
        return String.format("%-10s %-20s %-10s %-10s", maSV, tenSV, lop, khoa);
    }

}
