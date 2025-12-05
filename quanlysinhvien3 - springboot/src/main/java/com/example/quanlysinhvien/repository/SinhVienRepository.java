package com.example.quanlysinhvien.repository;

import com.example.quanlysinhvien.entity.KetQuaHocTap;
import com.example.quanlysinhvien.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, Long> {


    @Query(
            value = "SELECT m.ten_mon " +
                    "FROM mon_hoc m " +
                    "JOIN ket_qua_hoc_tap k on m.ma_mon = k.ma_mon " +
                    "WHERE k.ma_sv = :ma_sv",
            nativeQuery = true
    )
    List<String> findMonHocDangKyByMaSV(@Param("ma_sv") Long masv);



    @Query("SELECT s.ten_sv " +
            "FROM SinhVien s")
    List<String> findAllTenSinhVien();


}

