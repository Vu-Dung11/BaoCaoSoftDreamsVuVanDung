package com.example.quanlysinhvien.controller;

import com.example.quanlysinhvien.dto.SinhVienDTO;
import com.example.quanlysinhvien.form.SinhVienCreateForm;
import com.example.quanlysinhvien.form.SinhVienUpdateForm;
import com.example.quanlysinhvien.service.SinhVienService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sinh-vien")
@AllArgsConstructor
@CrossOrigin("*")
public class SinhVienController {

    private SinhVienService sinhVienService;

    @PostMapping
    public ResponseEntity<SinhVienDTO> createSinhVien(@Valid @RequestBody SinhVienCreateForm form) {
        SinhVienDTO dto = sinhVienService.createSinhVien(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SinhVienDTO> updateSinhVien(
            @PathVariable Long id,
            @Valid @RequestBody SinhVienUpdateForm form) {
        SinhVienDTO dto = sinhVienService.updateSinhVien(id, form);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SinhVienDTO> getSinhVienById(@PathVariable Long id) {
        SinhVienDTO dto = sinhVienService.getSinhVienById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<SinhVienDTO>> getAllSinhVien() {
        List<SinhVienDTO> dtos = sinhVienService.getAllSinhVien();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/tensv")
    public ResponseEntity<List<String>> getAllSinhVienName() {
       var listName = sinhVienService.getAllSinhVienName();
       return ResponseEntity.ok(listName);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSinhVien(@PathVariable Long id) {
        sinhVienService.deleteSinhVien(id);
        return ResponseEntity.noContent().build();
    }

    //Xem số môn học sinh viên đăng ký -
    @GetMapping("/ten-mon/{id}")
    public ResponseEntity<List<String>> findMonHocDangKyByMaSV( @PathVariable Long id) {
        var listSV = sinhVienService.findMonHocDangKyByMaSV(id);
        return ResponseEntity.ok(listSV);
    }


}

