import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLySinhVienImpl implements quanLySinhVien {
    private List<SinhVien> listSv = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);


    @Override
    public List<SinhVien> getListSv() {
        return listSv;
    }

    @Override
    public SinhVien getSvById(Long maSv) {
        for (SinhVien sv : listSv) {
            if (sv.getMaSV().equals(maSv)) {
                return sv;
            }
        }
        return null;
    }

    @Override
    public List<MonHoc> getListMonHocIsRegis(Long maSv) {
        SinhVien Sv = getSvById(maSv);
        System.out.println("các môn học đã đăng ký của sinh viên " + Sv.getTenSV() + ": ");
        List<MonHoc> listMonHoc = new ArrayList<>();
        for (KetQuaHocTap kq : Sv.getBangDiem()) {
            listMonHoc.add(kq.getMonHoc());
        }
        return listMonHoc;
    }

    @Override
    public List<KetQuaHocTap> getSvResults(Long maSv) {
        SinhVien sv = getSvById(maSv);
        return sv.getBangDiem();

    }


    int thuTuMonHocCanSua(Long maSv) {
        SinhVien sv = getSvById(maSv);
        var listBangDiem = sv.getBangDiem();
        var i = 1;
        for (KetQuaHocTap hocTap : listBangDiem) {
            System.out.println(i + "." + hocTap.getMonHoc().getTenMon());
            i++;
        }
        System.out.print("nhập số thứ tự môn học cần sửa: ");
        return scanner.nextInt();
    }

    @Override
    public Boolean InputSvMark(Long maSv) {
        SinhVien sv = getSvById(maSv);
        var listBangDiem = sv.getBangDiem();
        var sott = thuTuMonHocCanSua(maSv);
        var index = sott - 1;
        if (index >= 0 && index < sv.getBangDiem().size()) {
            System.out.print("nhập điểm quá trình: ");
            var diemQT = scanner.nextDouble();
            System.out.print("nhập điểm thành phần: ");
            var diemTP = scanner.nextDouble();
            listBangDiem.get(index).nhapDiem(diemQT, diemTP);
            return true;
        }
        return false;
    }

    @Override
    public Boolean CheckPass(Long maSv) {

        var sv = getSvById(maSv);
        var listBangDiem = sv.getBangDiem();
        System.out.println("kết quả sv: ");
        for (KetQuaHocTap kq : listBangDiem) {
            if (kq.isQuaMon())
                System.out.println(kq
                        .getMonHoc()
                        .getTenMon() + ": đỗ");
            else System.out.println(kq.getMonHoc().getTenMon() + ": trượt");
        }
        return null;
    }


}
