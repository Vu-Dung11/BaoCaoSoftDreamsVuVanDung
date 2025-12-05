package com.example.quanlysinhvien.form;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class MonHocUpdateForm {
    @NotBlank(message = "Tên môn học không được để trống")
    @Size(max = 30, message = "Tên môn học không được vượt quá 30 ký tự")
    private String name;
    
    @NotNull(message = "Tỷ lệ điểm quá trình không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tỷ lệ điểm quá trình phải lớn hơn hoặc bằng 0")
    @DecimalMax(value = "1.0", inclusive = true, message = "Tỷ lệ điểm quá trình phải nhỏ hơn hoặc bằng 1")
    private Double ty_le_diem_qua_trinh;
}

