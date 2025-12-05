package com.example.quanlysinhvien.controller;

import com.example.quanlysinhvien.dto.KetQuaHocTapDTO;
import com.example.quanlysinhvien.dto.TruotDoDTO;
import com.example.quanlysinhvien.form.KetQuaHocTapCreateForm;
import com.example.quanlysinhvien.form.KetQuaHocTapUpdateForm;
import com.example.quanlysinhvien.form.UpdateDiemRequest;
import com.example.quanlysinhvien.service.KetQuaHocTapService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ket-qua-hoc-tap")
@AllArgsConstructor
@CrossOrigin("*")
public class KetQuaHocTapController {

    private KetQuaHocTapService ketQuaHocTapService;

    @PostMapping
    public ResponseEntity<KetQuaHocTapDTO> createKetQuaHocTap(@Valid @RequestBody KetQuaHocTapCreateForm form) {
        KetQuaHocTapDTO dto = ketQuaHocTapService.createKetQuaHocTap(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KetQuaHocTapDTO> updateKetQuaHocTap(
            @PathVariable Long id,
            @Valid @RequestBody KetQuaHocTapUpdateForm form) {
        KetQuaHocTapDTO dto = ketQuaHocTapService.updateKetQuaHocTap(id, form);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KetQuaHocTapDTO> getKetQuaHocTapById(@PathVariable Long id) {
        KetQuaHocTapDTO dto = ketQuaHocTapService.getKetQuaHocTapById(id);
        return ResponseEntity.ok(dto);
    }

    // lấy kết quả học tập theo sinh viên id . Xem điểm môn học của sinh viên
    @GetMapping("/sinh-vien/{id}")
    public ResponseEntity<List<KetQuaHocTapDTO>> getAllKetQuaHocTapBySinhVienId(
            @PathVariable Long id
    ) {
        var dto = ketQuaHocTapService.getKetQuaHocTapBySinhVienId(id);
        return ResponseEntity.ok(dto);
    }

    // xem kết quả trượt đỗ
    @GetMapping("/sinh-vien/truot-do/{id}")
    public ResponseEntity<List<TruotDoDTO>> getKetQuaMonHoc(
            @PathVariable Long id
    ) {
        var dto = ketQuaHocTapService.getKetQuaMonHoc(id);
        return ResponseEntity.ok(dto);
    }


    @GetMapping
    public ResponseEntity<List<KetQuaHocTapDTO>> getAllKetQuaHocTap() {
        List<KetQuaHocTapDTO> dtos = ketQuaHocTapService.getAllKetQuaHocTap();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/update-diem")
    public ResponseEntity<Void> capNhatDiem(@RequestBody UpdateDiemRequest request) {
        ketQuaHocTapService.capNhatDiem(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKetQuaHocTap(@PathVariable Long id) {
        ketQuaHocTapService.deleteKetQuaHocTap(id);
        return ResponseEntity.noContent().build();
    }


}

