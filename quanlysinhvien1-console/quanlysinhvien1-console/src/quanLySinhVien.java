import java.util.List;

public interface quanLySinhVien {
    List<SinhVien> getListSv();

    SinhVien getSvById(Long maSv);

    List<MonHoc> getListMonHocIsRegis(Long maSv);

    List<KetQuaHocTap> getSvResults(Long maSv);

    Boolean InputSvMark(Long maSv);

    Boolean CheckPass(Long maSv);
}
