package com.example.quanlysinhvien.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "mon_hoc")
public class MonHoc {
    @Id
    @Column(name = "ma_mon")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ma_mon;// tự động sinh giá trị tăng 1

    @Column(name = "ten_mon", length = 30, nullable = false, unique = true)
    private String name;

    @Column(name = "ty_le_diem_qua_trinh", nullable = false)
    private Double ty_le_diem_qua_trinh;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

