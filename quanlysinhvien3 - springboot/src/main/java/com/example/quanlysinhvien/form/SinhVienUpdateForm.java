package com.example.quanlysinhvien.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class SinhVienUpdateForm {
    @NotBlank(message = "Tên sinh viên không được để trống")
    @Size(max = 30, message = "Tên sinh viên không được vượt quá 30 ký tự")
    private String ten_sv;
    
    @NotBlank(message = "Giới tính không được để trống")
    @Size(max = 10, message = "Giới tính không được vượt quá 10 ký tự")
    @Pattern(regexp = "^(Nam|Nữ|nam|nữ|NAM|NỮ)$", message = "Giới tính phải là 'Nam' hoặc 'Nữ'")
    private String gioi_tinh;
    
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private LocalDate ngay_sinh;
    
    @Size(max = 30, message = "Lớp không được vượt quá 30 ký tự")
    private String lop;
    
    @Size(max = 30, message = "Khoa không được vượt quá 30 ký tự")
    private String khoa;
}

