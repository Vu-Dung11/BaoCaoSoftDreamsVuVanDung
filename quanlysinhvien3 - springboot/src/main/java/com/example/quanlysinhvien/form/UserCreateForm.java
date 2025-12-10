package com.example.quanlysinhvien.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UserCreateForm {

    @NotBlank
    @Length(max = 50)
    private String username;

    @NotBlank
    @Length(max = 50)
    private String email;

    @NotBlank
    @Length(min = 8, max = 32)
    private String password;
    @NotBlank
    @Pattern(regexp = "ADMIN|TEACHER")
    private String role;
}
