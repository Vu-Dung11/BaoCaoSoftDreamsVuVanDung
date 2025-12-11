package com.example.quanlysinhvien.repository;

import com.example.quanlysinhvien.domain.SinhVien;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the SinhVien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, Long> {

    @Query(
        value = "SELECT m.ten_mon " +
            "FROM mon_hoc m " +
            "JOIN ket_qua_hoc_tap k on m.id = k.mon_hoc_id " +
            "WHERE k.sinh_vien_id = :id",
        nativeQuery = true
    )
    List<String> findMonHocDangKyByMaSV(@Param("id") Long masv);



}
