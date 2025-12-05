package com.example.quanlysinhvien.form;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class KetQuaHocTapCreateForm {

    @DecimalMin(value = "0.0", inclusive = true, message = "Điểm quá trình phải lớn hơn hoặc bằng 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Điểm quá trình phải nhỏ hơn hoặc bằng 10")
    private Double diem_qua_trinh;
    
    @DecimalMin(value = "0.0", inclusive = true, message = "Điểm thành phần phải lớn hơn hoặc bằng 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Điểm thành phần phải nhỏ hơn hoặc bằng 10")
    private Double diem_thanh_phan;
    
    @NotNull(message = "Mã sinh viên không được để trống")
    private Long sinhVienId;
    
    @NotNull(message = "Mã môn học không được để trống")

    private Long monHocId;
}

