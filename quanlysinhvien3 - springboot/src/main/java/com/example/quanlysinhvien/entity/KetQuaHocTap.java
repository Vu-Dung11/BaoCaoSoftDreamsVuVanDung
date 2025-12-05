package com.example.quanlysinhvien.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ket_qua_hoc_tap")
public class KetQuaHocTap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "diem_qua_trinh")
    private Double diem_qua_trinh;


    @Column(name = "diem_thanh_phan")
    private Double diem_thanh_phan;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

  @ManyToOne
  @JoinColumn(name = "ma_sv",
          //unique = true
          nullable = false
  )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "ma_mon",
            //unique = true
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MonHoc monHoc;









}