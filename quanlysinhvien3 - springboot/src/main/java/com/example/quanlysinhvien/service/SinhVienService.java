package com.example.quanlysinhvien.service;

import com.example.quanlysinhvien.dto.SinhVienBasicDTO;
import com.example.quanlysinhvien.dto.SinhVienDTO;
import com.example.quanlysinhvien.entity.KetQuaHocTap;
import com.example.quanlysinhvien.entity.SinhVien;
import com.example.quanlysinhvien.form.SinhVienCreateForm;
import com.example.quanlysinhvien.form.SinhVienUpdateForm;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SinhVienService {
    SinhVienDTO createSinhVien(SinhVienCreateForm form);
    SinhVienDTO updateSinhVien(Long id, SinhVienUpdateForm form);
    SinhVienDTO getSinhVienById(Long id);
    List<SinhVienDTO> getAllSinhVien();
    void deleteSinhVien(Long id);
    List<String> findMonHocDangKyByMaSV(Long masv);
    List<SinhVienBasicDTO> getAllSinhVienName();
    List<SinhVienDTO> searchSinhVienByTen(String ten_sv);

}

