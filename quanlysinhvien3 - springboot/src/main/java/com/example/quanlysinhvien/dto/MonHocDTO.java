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
public class MonHocDTO {
    private Long ma_mon;
    private String name;
    private Double ty_le_diem_qua_trinh;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

