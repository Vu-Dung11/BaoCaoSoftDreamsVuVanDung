package com.example.quanlysinhvien.services;

import com.example.quanlysinhvien.dto.SinhVienBasicDTO;
import com.example.quanlysinhvien.dto.SinhVienDTO;
import com.example.quanlysinhvien.entity.KetQuaHocTap;
import com.example.quanlysinhvien.entity.SinhVien;
import com.example.quanlysinhvien.form.SinhVienCreateForm;
import com.example.quanlysinhvien.form.SinhVienUpdateForm;
import com.example.quanlysinhvien.form.UpdateDiemRequest;
import com.example.quanlysinhvien.repository.SinhVienRepository;
import com.example.quanlysinhvien.service.SinhVienService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SinhVienServiceImpl implements SinhVienService {

    private SinhVienRepository sinhVienRepository;
    private ModelMapper modelMapper;

    @Override
    public SinhVienDTO createSinhVien(SinhVienCreateForm form) {
        SinhVien sinhVien = modelMapper.map(form, SinhVien.class);
        SinhVien savedSinhVien = sinhVienRepository.save(sinhVien);
        return modelMapper.map(savedSinhVien, SinhVienDTO.class);
    }

    @Override
    public SinhVienDTO updateSinhVien(Long id, SinhVienUpdateForm form) {
        SinhVien sinhVien = sinhVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với id: " + id));
        
        modelMapper.map(form, sinhVien);
        SinhVien updatedSinhVien = sinhVienRepository.save(sinhVien);
        return modelMapper.map(updatedSinhVien, SinhVienDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public SinhVienDTO getSinhVienById(Long id) {
        SinhVien sinhVien = sinhVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với id: " + id));
        return modelMapper.map(sinhVien, SinhVienDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SinhVienDTO> getAllSinhVien() {
        List<SinhVien> sinhViens = sinhVienRepository.findAll();
        return sinhViens.stream()
                .map(sinhVien -> modelMapper.map(sinhVien, SinhVienDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSinhVien(Long id) {
        if (!sinhVienRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sinh viên với id: " + id);
        }
        sinhVienRepository.deleteById(id);
    }

    @Override
    public List<String> findMonHocDangKyByMaSV(Long masv) {
        var listMH = sinhVienRepository.findMonHocDangKyByMaSV(masv);
        return listMH;
    }

    @Override
    public List<SinhVienBasicDTO> getAllSinhVienName() {
        var listNameSV = sinhVienRepository.findAllTenSinhVien();
        return listNameSV.stream().map(s -> modelMapper.map(s, SinhVienBasicDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<SinhVienDTO> searchSinhVienByTen(String ten_sv) {
        var listSv = sinhVienRepository.searchSinhVienByTen(ten_sv);
        return   listSv.stream().map(s -> modelMapper.map(s, SinhVienDTO.class)).collect(Collectors.toList());
    }


}

