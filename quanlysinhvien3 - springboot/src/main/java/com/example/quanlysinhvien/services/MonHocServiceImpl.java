package com.example.quanlysinhvien.services;

import com.example.quanlysinhvien.dto.MonHocDTO;
import com.example.quanlysinhvien.entity.MonHoc;
import com.example.quanlysinhvien.form.MonHocCreateForm;
import com.example.quanlysinhvien.form.MonHocUpdateForm;
import com.example.quanlysinhvien.repository.MonHocRepository;
import com.example.quanlysinhvien.service.MonHocService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class MonHocServiceImpl implements MonHocService {

    private MonHocRepository monHocRepository;

    private ModelMapper modelMapper;

    @Override
    public MonHocDTO createMonHoc(MonHocCreateForm form) {
        MonHoc monHoc = modelMapper.map(form, MonHoc.class);
        MonHoc savedMonHoc = monHocRepository.save(monHoc);
        return modelMapper.map(savedMonHoc, MonHocDTO.class);
    }

    @Override
    public MonHocDTO updateMonHoc(Long id, MonHocUpdateForm form) {
        MonHoc monHoc = monHocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học với id: " + id));

        modelMapper.map(form, monHoc);
        MonHoc updatedMonHoc = monHocRepository.save(monHoc);
        return modelMapper.map(updatedMonHoc, MonHocDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public MonHocDTO getMonHocById(Long id) {
        MonHoc monHoc = monHocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học với id: " + id));
        return modelMapper.map(monHoc, MonHocDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonHocDTO> getAllMonHoc() {
        List<MonHoc> monHocs = monHocRepository.findAll();
        return monHocs.stream()
                .map(monHoc -> modelMapper.map(monHoc, MonHocDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMonHoc(Long id) {
        if (!monHocRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy môn học với id: " + id);
        }
        monHocRepository.deleteById(id);
    }
}

