import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    static Scanner sc = new Scanner(System.in);

    static QuanLySinhVienImpl quanLySinhVien = new QuanLySinhVienImpl();


    static void khoiTaoDuLieuMau() {
        MonHoc lapTrinhJava = new MonHoc("Lập trình Java", 0.4);
        MonHoc cauTrucDuLieu = new MonHoc("Cấu trúc dữ liệu", 0.3);
        MonHoc csdl = new MonHoc("Cơ sở dữ liệu", 0.35);
        MonHoc heDieuHanh = new MonHoc("Hệ điều hành", 0.25);

        SinhVien sv1 = new SinhVien(
                (long) 1,
                "Nguyễn Văn An",
                "Nam",
                "10/02/2004",
                "CNTT1",
                "K17",
                new ArrayList<>()
        );
        sv1.dangKyMonHoc(lapTrinhJava);
        sv1.dangKyMonHoc(cauTrucDuLieu);
        sv1.getBangDiem().get(0).nhapDiem(7.5, 8.0);
        sv1.getBangDiem().get(1).nhapDiem(6.5, 7.0);

        SinhVien sv2 = new SinhVien(
                (long) 2,
                "Trần Thị Bình",
                "Nữ",
                "23/05/2004",
                "CNTT2",
                "K17",
                new ArrayList<>()
        );
        sv2.dangKyMonHoc(lapTrinhJava);
        sv2.dangKyMonHoc(csdl);
        sv2.dangKyMonHoc(heDieuHanh);
        sv2.getBangDiem().get(0).nhapDiem(8.0, 8.5);
        sv2.getBangDiem().get(1).nhapDiem(7.0, 6.5);
        sv2.getBangDiem().get(2).nhapDiem(6.0, 7.5);

        SinhVien sv3 = new SinhVien(
                (long) 3,
                "Lê Hữu Cường",
                "Nam",
                "01/09/2003",
                "CNTT3",
                "K16",
                new ArrayList<>()
        );
        sv3.dangKyMonHoc(cauTrucDuLieu);
        sv3.dangKyMonHoc(csdl);
        sv3.getBangDiem().get(0).nhapDiem(5.5, 5.0);
        sv3.getBangDiem().get(1).nhapDiem(4.5, 6.0);

        quanLySinhVien.getListSv().add(sv1);
        quanLySinhVien.getListSv().add(sv2);
        quanLySinhVien.getListSv().add(sv3);
    }

    public static void main(String[] args) {
        khoiTaoDuLieuMau();
        while (true) {
            System.out.println("CHƯƠNG TRÌNH QUẢN LÝ SINH VIÊN");
            System.out.println("1.Xem danh sách sinh viên");
            System.out.println("2.Xem chi tiết sinh viên");
            System.out.println("3.Xem số môn học sinh viên đăng ký");
            System.out.println("4.Xem điểm môn học của sinh viên");
            System.out.println("5.Nhập điểm của sinh viên");
            System.out.println("6.Xem kết quả trượt đỗ của sinh viên");
            System.out.println("0.Kết thúc chương trình");
            System.out.print("Nhập lựa chọn của bạn:");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Danh sách các sinh viên");

                    for (SinhVien sv : quanLySinhVien.getListSv()) {
                        System.out.println(sv.getTenSV());

                    }
                    System.out.println("======done======");
                    break;
                case 2:
                    System.out.print("nhập mã sinh viên cần xem chi tiết");
                    Long msv = sc.nextLong();
                    var sinhvien = quanLySinhVien.getSvById(msv);
                    System.out.println("thông tin sinh viên chi tiết: ");
                    System.out.println(sinhvien.toString());
                    System.out.println("======done======");
                    break;

                case 3:
                    System.out.print("Nhập mã sinh viên: ");
                    Long msv2 = sc.nextLong();
                    var listMh = quanLySinhVien.getListMonHocIsRegis(msv2);
                    for (MonHoc mh : listMh) {
                        System.out.println(mh.getTenMon());
                    }
                    System.out.println("======done======");
                    break;
                case 4:
//                    System.out.println("xem điểm môn học của sinh viên:");
                    System.out.print("Nhập mã sinh viên: ");
                    Long msv3 = sc.nextLong();
                    var sv = quanLySinhVien.getSvById(msv3);
                    var listMh1 = sv.getBangDiem();
                    for (KetQuaHocTap ketQuaHocTap : listMh1) {
                        System.out.println(ketQuaHocTap.toString());
                    }
                    System.out.println("======done======");

                    break;
                case 5:
                    System.out.println("mời bạn nhập điểm cho sinh viên");
                    System.out.print("Nhập mã sinh viên: ");
                    Long msv4 = sc.nextLong();
                    if (quanLySinhVien.InputSvMark(msv4)) System.out.println("nhập điểm thành công");
                    else System.out.println("nhập điểm không thành công");
                    System.out.println("======done======");

                    break;
                case 6:
                    System.out.println("mời bạn nhập điểm cho sinh viên");

                    System.out.print("Nhập mã sinh viên: ");
                    Long msv5 = sc.nextLong();
                    quanLySinhVien.CheckPass(msv5);
                    System.out.println("======done======");

                    break;
                case 0:
                    System.out.println("Kết thúc chương trình");
                    return;
            }
        }


    }
}
