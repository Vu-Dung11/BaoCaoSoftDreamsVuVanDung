package com.example.quanlysinhvien.service;

import com.example.quanlysinhvien.dto.KetQuaHocTapDTO;
import com.example.quanlysinhvien.dto.TruotDoDTO;
import com.example.quanlysinhvien.entity.KetQuaHocTap;
import com.example.quanlysinhvien.form.KetQuaHocTapCreateForm;
import com.example.quanlysinhvien.form.KetQuaHocTapUpdateForm;
import com.example.quanlysinhvien.form.UpdateDiemRequest;

import java.util.List;

public interface KetQuaHocTapService {
    KetQuaHocTapDTO createKetQuaHocTap(KetQuaHocTapCreateForm form);
    KetQuaHocTapDTO updateKetQuaHocTap(Long id, KetQuaHocTapUpdateForm form);
    KetQuaHocTapDTO getKetQuaHocTapById(Long id);
    List<KetQuaHocTapDTO> getAllKetQuaHocTap();
    void deleteKetQuaHocTap(Long id);
    List<KetQuaHocTapDTO> getKetQuaHocTapBySinhVienId(Long studentId);
    void capNhatDiem(UpdateDiemRequest request);
    List<TruotDoDTO> getKetQuaMonHoc(Long maSv);

}

