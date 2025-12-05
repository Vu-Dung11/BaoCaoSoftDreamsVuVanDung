package com.example.quanlysinhvien.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class KetQuaHocTapByMasvDTO {
        // Thứ tự và kiểu dữ liệu phải khớp với truy vấn
        private Double diemQuaTrinh;
        private Double diemThanhPhan;
        private String tenMon;
        private Double tyLeDiemQuaTrinh;
}
