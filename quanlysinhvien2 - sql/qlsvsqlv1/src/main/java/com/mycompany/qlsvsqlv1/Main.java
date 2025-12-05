package com.mycompany.qlsvsqlv1;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    // Sử dụng interface, có thể dễ dàng chuyển đổi giữa các implementation
    static quanLySinhVien quanLySinhVien = new QuanLySinhVienImpl();

    public static void main(String[] args) {
        // Kiểm tra kết nối CSDL ngay khi bắt đầu
        if (DatabaseConnector.getConnection() == null) {
            System.err.println("Không thể khởi động ứng dụng do lỗi kết nối CSDL.");
            return;
        }

        while (true) {
            System.out.println("\n===== CHƯƠNG TRÌNH QUẢN LÝ SINH VIÊN (MySQL) =====");
            System.out.println("1. Xem danh sách sinh viên");
            System.out.println("2. Xem chi tiết sinh viên");
            System.out.println("3. Xem các môn học sinh viên đã đăng ký");
            System.out.println("4. Xem bảng điểm của sinh viên");
            System.out.println("5. Nhập điểm cho sinh viên");
            System.out.println("6. Kiểm tra kết quả qua môn của sinh viên");
            System.out.println("0. Kết thúc chương trình");
            System.out.print(">> Vui lòng chọn một chức năng: ");

            // Bắt lỗi nhập không phải số
            if (!sc.hasNextInt()) {
                System.out.println("Lựa chọn không hợp lệ, vui lòng nhập số.");
                sc.next(); // Xóa token không hợp lệ
                continue;
            }
            int choice = sc.nextInt();
            sc.nextLine(); // Tiêu thụ ký tự newline còn lại

            switch (choice) {
                case 1:
                    xemDanhSachSinhVien();
                    break;
                case 2:
                    xemChiTietSinhVien();
                    break;
                case 3:
                    xemMonHocDaDangKy();
                    break;
                case 4:
                    xemBangDiem();
                    break;
                case 5:
                    nhapDiemSinhVien();
                    break;
                case 6:
                    kiemTraKetQua();
                    break;
                case 0:
                    System.out.println("Đang đóng kết nối và kết thúc chương trình...");
                    DatabaseConnector.closeConnection();
                    System.out.println("Đã thoát.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại.");
            }
            System.out.println("==================================================");
        }
    }

    private static void xemDanhSachSinhVien() {
        System.out.println("\n--- Danh sách sinh viên ---");
        List<SinhVien> danhSach = quanLySinhVien.getListSv();
        if (danhSach.isEmpty()) {
            System.out.println("Chưa có sinh viên nào trong hệ thống.");
        } else {
            System.out.printf("%-10s | %-25s | %-10s | %-10s\n", "Mã SV", "Họ Tên", "Lớp", "Khóa");
            System.out.println("---------------------------------------------------------");
            for (SinhVien sv : danhSach) {
                System.out.printf("%-10d | %-25s | %-10s | %-10s\n",
                        sv.getMaSV(), sv.getTenSV(), sv.getLop(), sv.getKhoa());
            }
        }
    }

    private static void xemChiTietSinhVien() {
        System.out.print("Nhập mã sinh viên cần xem chi tiết: ");
        Long msv = sc.nextLong();
        sc.nextLine(); 
        SinhVien sv = quanLySinhVien.getSvById(msv);
        if (sv != null) {
            System.out.println("\n--- Thông tin chi tiết sinh viên ---");
            System.out.println("Mã SV: " + sv.getMaSV());
            System.out.println("Họ tên: " + sv.getTenSV());
            System.out.println("Giới tính: " + sv.getGioiTinh());
            System.out.println("Ngày sinh: " + sv.getNgaySinh());
            System.out.println("Lớp: " + sv.getLop());
            System.out.println("Khóa: " + sv.getKhoa());
        } else {
            System.out.println("Không tìm thấy sinh viên có mã " + msv);
        }
    }

    private static void xemMonHocDaDangKy() {
        System.out.print("Nhập mã sinh viên: ");
        Long msv = sc.nextLong();
        sc.nextLine(); 
        // Kiểm tra xem SV có tồn tại không
        if (quanLySinhVien.getSvById(msv) == null) {
            System.out.println("Không tìm thấy sinh viên có mã " + msv);
            return;
        }

        List<MonHoc> listMh = quanLySinhVien.getListMonHocIsRegis(msv);
        if (listMh.isEmpty()) {
            System.out.println("Sinh viên này chưa đăng ký môn học nào.");
        } else {
            System.out.println("\n--- Các môn học đã đăng ký ---");
            for (MonHoc mh : listMh) {
                System.out.println("- " + mh.getTenMon());
            }
        }
    }

    private static void xemBangDiem() {
        System.out.print("Nhập mã sinh viên: ");
        Long msv = sc.nextLong();
        sc.nextLine(); 
        List<KetQuaHocTap> bangDiem = quanLySinhVien.getSvResults(msv);
        if (bangDiem != null && !bangDiem.isEmpty()) {
            System.out.println("\n--- Bảng điểm của sinh viên ---");
            System.out.printf("%-25s | %-10s | %-10s | %-10s\n", "Tên Môn Học", "Điểm QT", "Điểm TP", "Tổng Kết");
            System.out.println("-------------------------------------------------------------------");
            for (KetQuaHocTap kq : bangDiem) {
                System.out.printf("%-25s | %-10.2f | %-10.2f | %-10.2f\n",
                        kq.getMonHoc().getTenMon(),
                        kq.getDiemQuaTrinh(),
                        kq.getDiemThanhPhan(),
                        kq.tinhDiemTongKet());
            }
        } else {
            System.out.println("Không tìm thấy thông tin điểm của sinh viên có mã " + msv + " hoặc sinh viên chưa có điểm.");
        }
    }

    private static void nhapDiemSinhVien() {
        System.out.print("Nhập mã sinh viên cần nhập điểm: ");
        Long msv = sc.nextLong();
        sc.nextLine(); 
        quanLySinhVien.InputSvMark(msv);
    }



    private static void kiemTraKetQua() {
        System.out.print("Nhập mã sinh viên để kiểm tra kết quả: ");
        Long msv = sc.nextLong();
        sc.nextLine(); 
        quanLySinhVien.CheckPass(msv);
    }
}