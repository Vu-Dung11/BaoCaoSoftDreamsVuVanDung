package com.example.quanlysinhvien.controller;

import com.example.quanlysinhvien.dto.MonHocDTO;
import com.example.quanlysinhvien.form.MonHocCreateForm;
import com.example.quanlysinhvien.form.MonHocUpdateForm;
import com.example.quanlysinhvien.service.MonHocService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mon-hoc")
@AllArgsConstructor
@CrossOrigin("*")
public class MonHocController {


    private MonHocService monHocService;

    @PostMapping
    public ResponseEntity<MonHocDTO> createMonHoc(@Valid @RequestBody MonHocCreateForm form) {
        MonHocDTO dto = monHocService.createMonHoc(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonHocDTO> updateMonHoc(
            @PathVariable Long id,
            @Valid @RequestBody MonHocUpdateForm form) {
        MonHocDTO dto = monHocService.updateMonHoc(id, form);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonHocDTO> getMonHocById(@PathVariable Long id) {
        MonHocDTO dto = monHocService.getMonHocById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<MonHocDTO>> getAllMonHoc() {
        List<MonHocDTO> dtos = monHocService.getAllMonHoc();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonHoc(@PathVariable Long id) {
        monHocService.deleteMonHoc(id);
        return ResponseEntity.noContent().build();
    }
}

