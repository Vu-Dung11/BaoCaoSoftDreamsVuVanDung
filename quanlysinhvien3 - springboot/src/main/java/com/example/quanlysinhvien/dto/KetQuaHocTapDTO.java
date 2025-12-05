package com.example.quanlysinhvien.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KetQuaHocTapDTO {
    private Long id;
    private Double diem_qua_trinh;
    private Double diem_thanh_phan;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Long sinhVienId;
    private Long monHocId;
    private String sinhVienTen;
    private String monHocTen;
}

