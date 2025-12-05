package com.mycompany.qlsvsqlv1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLySinhVienImpl implements quanLySinhVien {

    static Scanner scanner = new Scanner(System.in);

    // Lấy kết nối từ lớp DatabaseConnector
    private Connection getConnection() {
        return DatabaseConnector.getConnection();
    }

    @Override
    public List<SinhVien> getListSv() {
        List<SinhVien> listSv = new ArrayList<>();
        String query = "SELECT * FROM sinh_vien";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SinhVien sv = new SinhVien();
                sv.setMaSV(rs.getLong("ma_sv"));
                sv.setTenSV(rs.getString("ten_sv"));
                sv.setGioiTinh(rs.getString("gioi_tinh"));
                sv.setNgaySinh(rs.getString("ngay_sinh"));
                sv.setLop(rs.getString("lop"));
                sv.setKhoa(rs.getString("khoa"));
                // Lưu ý: Bảng điểm sẽ được lấy khi cần thiết để tối ưu hiệu năng
                sv.setBangDiem(new ArrayList<>()); // Khởi tạo rỗng
                listSv.add(sv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listSv;
    }

    @Override
    public SinhVien getSvById(Long maSv) {
        String query = "SELECT * FROM sinh_vien WHERE ma_sv = ?";
        SinhVien sv = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, maSv);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sv = new SinhVien();
                    sv.setMaSV(rs.getLong("ma_sv"));
                    sv.setTenSV(rs.getString("ten_sv"));
                    sv.setGioiTinh(rs.getString("gioi_tinh"));
                    sv.setNgaySinh(rs.getString("ngay_sinh"));
                    sv.setLop(rs.getString("lop"));
                    sv.setKhoa(rs.getString("khoa"));
                    // Lấy danh sách kết quả học tập của sinh viên
                    sv.setBangDiem(getSvResults(maSv));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sv;
    }

    @Override
    public List<MonHoc> getListMonHocIsRegis(Long maSv) {
        List<MonHoc> listMonHoc = new ArrayList<>();

        String query = "SELECT mh.ma_mon, mh.ten_mon, mh.ty_le_diem_qua_trinh " +
                       "FROM mon_hoc mh " +
                       "JOIN ket_qua_hoc_tap kqht ON mh.ma_mon = kqht.ma_mon " +
                       "WHERE kqht.ma_sv = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, maSv);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Các môn học đã đăng ký của sinh viên có mã " + maSv + ":");
                while (rs.next()) {
                    int maMon = rs.getInt("ma_mon");
                    String tenMon = rs.getString("ten_mon");
                    double tyLe = rs.getDouble("ty_le_diem_qua_trinh");
                    listMonHoc.add(new MonHoc(maMon, tenMon, tyLe));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listMonHoc;
    }

    @Override
    public List<KetQuaHocTap> getSvResults(Long maSv) {
        List<KetQuaHocTap> bangDiem = new ArrayList<>();
        String query = "SELECT kqht.*, mh.ten_mon, mh.ty_le_diem_qua_trinh " +
                       "FROM ket_qua_hoc_tap kqht " +
                       "JOIN mon_hoc mh ON kqht.ma_mon = mh.ma_mon " +
                       "WHERE kqht.ma_sv = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, maSv);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int maMon = rs.getInt("ma_mon");
                    String tenMon = rs.getString("ten_mon");
                    double tyLe = rs.getDouble("ty_le_diem_qua_trinh");
                    MonHoc monHoc = new MonHoc(maMon, tenMon, tyLe);

                    KetQuaHocTap kq = new KetQuaHocTap(monHoc);
                    kq.setDiemQuaTrinh(rs.getDouble("diem_qua_trinh"));
                    kq.setDiemThanhPhan(rs.getDouble("diem_thanh_phan"));
                    bangDiem.add(kq);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bangDiem;
    }

    @Override
    public Boolean InputSvMark(Long maSv) {
        SinhVien sv = getSvById(maSv);
        if (sv == null) {
            System.out.println("Không tìm thấy sinh viên với mã: " + maSv);
            return false;
        }

        List<KetQuaHocTap> listBangDiem = sv.getBangDiem();
        if (listBangDiem.isEmpty()) {
            System.out.println("Sinh viên này chưa đăng ký môn học nào.");
            return false;
        }

        // Hiển thị danh sách môn học để người dùng chọn
        System.out.println("Chọn môn học cần nhập điểm cho sinh viên " + sv.getTenSV() + ":");
        for (int i = 0; i < listBangDiem.size(); i++) {
            System.out.println((i + 1) + ". " + listBangDiem.get(i).getMonHoc().getTenMon());
        }
        System.out.print("Nhập số thứ tự môn học: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice < 1 || choice > listBangDiem.size()) {
            System.out.println("Lựa chọn không hợp lệ.");
            return false;
        }

        KetQuaHocTap ketQuaCanSua = listBangDiem.get(choice - 1);
        int maMon = ketQuaCanSua.getMonHoc().getMaMon();

        System.out.print("Nhập điểm quá trình: ");
        double diemQT = scanner.nextDouble();
        System.out.print("Nhập điểm thành phần: ");
        double diemTP = scanner.nextDouble();
        scanner.nextLine(); 

        // Cập nhật điểm vào CSDL
        String query = "UPDATE ket_qua_hoc_tap SET diem_qua_trinh = ?, diem_thanh_phan = ? " +
                       "WHERE ma_sv = ? AND ma_mon = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, diemQT);
            ps.setDouble(2, diemTP);
            ps.setLong(3, maSv);
            ps.setInt(4, maMon);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Nhập điểm thành công!");
                // Cập nhật lại đối tượng trong bộ nhớ
                ketQuaCanSua.nhapDiem(diemQT, diemTP);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Nhập điểm thất bại.");
        return false;
    }

    @Override
    public Boolean CheckPass(Long maSv) {
        SinhVien sv = getSvById(maSv);
        if (sv == null) {
            System.out.println("Không tìm thấy sinh viên.");
            return false;
        }

        System.out.println("Kết quả học tập của sinh viên: " + sv.getTenSV());
        List<KetQuaHocTap> listBangDiem = sv.getBangDiem();
        if (listBangDiem.isEmpty()) {
            System.out.println("Sinh viên này chưa có điểm.");
            return true; 
        }

        for (KetQuaHocTap kq : listBangDiem) {
            String trangThai = kq.isQuaMon() ? "ĐỖ" : "TRƯỢT";
            System.out.printf("- Môn %s: %.2f (Tổng kết) -> %s\n",
                    kq.getMonHoc().getTenMon(),
                    kq.tinhDiemTongKet(),
                    trangThai);
        }
        return true;
    }
}