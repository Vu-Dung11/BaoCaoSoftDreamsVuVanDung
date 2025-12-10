package com.example.quanlysinhvien.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sinh_vien")
public class SinhVien {
    @Id
    @Column(name = "ma_sv")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ma_sv;// tự động sinh giá trị tăng 1

    @Column(name = "ten_sv", length = 30, nullable = false)
    private String ten_sv;

    @Column(name = "gioi_tinh", length = 10, nullable = false)
    private String gioi_tinh;

    @Column(name = "ngay_sinh")
    private LocalDate ngay_sinh;

    @Column(name = "lop", length = 30)
    private String lop;

    @Column(name = "khoa", length = 30)
    private String khoa;

    @Column(name = "created_at", nullable = true, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "sinhVien")
    private List<KetQuaHocTap> listKetQuaHocTap;
}
