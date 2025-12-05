package com.example.quanlysinhvien.services;

import com.example.quanlysinhvien.dto.KetQuaHocTapDTO;
import com.example.quanlysinhvien.dto.TruotDoDTO;
import com.example.quanlysinhvien.entity.KetQuaHocTap;
import com.example.quanlysinhvien.entity.MonHoc;
import com.example.quanlysinhvien.entity.SinhVien;
import com.example.quanlysinhvien.form.KetQuaHocTapCreateForm;
import com.example.quanlysinhvien.form.KetQuaHocTapUpdateForm;
import com.example.quanlysinhvien.form.UpdateDiemRequest;
import com.example.quanlysinhvien.repository.KetQuaHocTapRepository;
import com.example.quanlysinhvien.repository.MonHocRepository;
import com.example.quanlysinhvien.repository.SinhVienRepository;
import com.example.quanlysinhvien.service.KetQuaHocTapService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor

public class KetQuaHocTapServiceImpl implements KetQuaHocTapService {


    private KetQuaHocTapRepository ketQuaHocTapRepository;


    private SinhVienRepository sinhVienRepository;


    private MonHocRepository monHocRepository;

    private ModelMapper modelMapper;

    @Override
    public KetQuaHocTapDTO createKetQuaHocTap(KetQuaHocTapCreateForm form) {
        KetQuaHocTap ketQuaHocTap = modelMapper.map(form, KetQuaHocTap.class);

        // Set SinhVien
        SinhVien sinhVien = sinhVienRepository.findById(form.getSinhVienId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với id: " + form.getSinhVienId()));
        ketQuaHocTap.setSinhVien(sinhVien);

        // Set MonHoc
        MonHoc monHoc = monHocRepository.findById(form.getMonHocId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học với id: " + form.getMonHocId()));
        ketQuaHocTap.setMonHoc(monHoc);

        KetQuaHocTap savedKetQuaHocTap = ketQuaHocTapRepository.save(ketQuaHocTap);
        return mapToDTO(savedKetQuaHocTap);
    }

    @Override
    public KetQuaHocTapDTO updateKetQuaHocTap(Long id, KetQuaHocTapUpdateForm form) {
        KetQuaHocTap ketQuaHocTap = ketQuaHocTapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kết quả học tập với id: " + id));

        // Update basic fields
        ketQuaHocTap.setDiem_qua_trinh(form.getDiem_qua_trinh());
        ketQuaHocTap.setDiem_thanh_phan(form.getDiem_thanh_phan());

        // Update SinhVien if changed
        if (!ketQuaHocTap.getSinhVien().getMa_sv().equals(form.getSinhVienId())) {
            SinhVien sinhVien = sinhVienRepository.findById(form.getSinhVienId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với id: " + form.getSinhVienId()));
            ketQuaHocTap.setSinhVien(sinhVien);
        }

        // Update MonHoc if changed
        if (!ketQuaHocTap.getMonHoc().getMa_mon().equals(form.getMonHocId())) {
            MonHoc monHoc = monHocRepository.findById(form.getMonHocId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học với id: " + form.getMonHocId()));
            ketQuaHocTap.setMonHoc(monHoc);
        }

        KetQuaHocTap updatedKetQuaHocTap = ketQuaHocTapRepository.save(ketQuaHocTap);
        return mapToDTO(updatedKetQuaHocTap);
    }

    @Override
    @Transactional(readOnly = true)
    public KetQuaHocTapDTO getKetQuaHocTapById(Long id) {
        KetQuaHocTap ketQuaHocTap = ketQuaHocTapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kết quả học tập với id: " + id));
        return mapToDTO(ketQuaHocTap);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KetQuaHocTapDTO> getAllKetQuaHocTap() {
        List<KetQuaHocTap> ketQuaHocTaps = ketQuaHocTapRepository.findAll();
        return ketQuaHocTaps.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteKetQuaHocTap(Long id) {
        if (!ketQuaHocTapRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy kết quả học tập với id: " + id);
        }
        ketQuaHocTapRepository.deleteById(id);
    }

    @Override
    public List<KetQuaHocTapDTO> getKetQuaHocTapBySinhVienId(Long studentId) {
        var ketQuaHocTaps = ketQuaHocTapRepository.findBySinhVienNative(studentId);
        return ketQuaHocTaps.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void capNhatDiem(UpdateDiemRequest request) {
        // Gọi repository
        int rowsAffected = ketQuaHocTapRepository.updateDiemBySinhVienAndMonHoc(
                request.getMaSv(),
                request.getMaMon(),
                request.getDiemQuaTrinh(),
                request.getDiemThanhPhan()
        );
        // Kiểm tra xem có bản ghi nào được update không (Optional)
        if (rowsAffected == 0) {
            throw new RuntimeException("Không tìm thấy sinh viên hoặc môn học để cập nhật!");
        }
    }

    @Override
    public List<TruotDoDTO> getKetQuaMonHoc(Long maSv) {
        var data = ketQuaHocTapRepository.findKetQuaBySinhVien(maSv);
        List<TruotDoDTO> result = new ArrayList<>();
        for (Object[] row : data) {
            String tenMon = (String) row[2];
            double diemQT = row[0] != null ? ((Number) row[0]).doubleValue() : 0;
            double diemTP = row[1] != null ? ((Number) row[1]).doubleValue() : 0;
            double tyLeQT = row[3] != null ? ((Number) row[3]).doubleValue() : 0.0;

            // Công thức tính điểm cuối kỳ
            double diemCK = diemQT * tyLeQT + diemTP * (1 - tyLeQT);

            boolean isDo = diemCK >= 5;

            result.add(new TruotDoDTO(tenMon,
                    diemQT,
                    diemTP,
                    diemCK,
                    isDo));
        }

        return result;
    }

    private KetQuaHocTapDTO mapToDTO(KetQuaHocTap ketQuaHocTap) {
        KetQuaHocTapDTO dto = modelMapper.map(ketQuaHocTap, KetQuaHocTapDTO.class);
        dto.setSinhVienId(ketQuaHocTap.getSinhVien().getMa_sv());
        dto.setMonHocId(ketQuaHocTap.getMonHoc().getMa_mon());
        dto.setSinhVienTen(ketQuaHocTap.getSinhVien().getTen_sv());
        dto.setMonHocTen(ketQuaHocTap.getMonHoc().getName());
        return dto;
    }


}

