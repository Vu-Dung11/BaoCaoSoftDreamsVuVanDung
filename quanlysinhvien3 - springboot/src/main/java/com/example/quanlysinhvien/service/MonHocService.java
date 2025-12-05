package com.example.quanlysinhvien.service;

import com.example.quanlysinhvien.dto.MonHocDTO;
import com.example.quanlysinhvien.form.MonHocCreateForm;
import com.example.quanlysinhvien.form.MonHocUpdateForm;

import java.util.List;

public interface MonHocService {
    MonHocDTO createMonHoc(MonHocCreateForm form);
    MonHocDTO updateMonHoc(Long id, MonHocUpdateForm form);
    MonHocDTO getMonHocById(Long id);
    List<MonHocDTO> getAllMonHoc();
    void deleteMonHoc(Long id);
}

