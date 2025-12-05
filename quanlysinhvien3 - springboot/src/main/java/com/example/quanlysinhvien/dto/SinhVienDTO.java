package com.example.quanlysinhvien.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SinhVienDTO {
    private Long ma_sv;
    private String ten_sv;
    private String gioi_tinh;
    private LocalDate ngay_sinh;
    private String lop;
    private String khoa;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

