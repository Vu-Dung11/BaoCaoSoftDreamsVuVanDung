package com.example.quanlysinhvien.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDiemRequest {
    private Long maSv;
    private Long maMon;
    private Double diemQuaTrinh;
    private Double diemThanhPhan;
}
