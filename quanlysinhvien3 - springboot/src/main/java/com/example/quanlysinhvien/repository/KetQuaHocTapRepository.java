package com.example.quanlysinhvien.repository;

import com.example.quanlysinhvien.entity.KetQuaHocTap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KetQuaHocTapRepository extends JpaRepository<KetQuaHocTap, Long> {
    @Query(
            value = "SELECT * FROM ket_qua_hoc_tap WHERE ma_sv = :ma_sv",
            nativeQuery = true
    )
    List<KetQuaHocTap> findBySinhVienNative(@Param("ma_sv") Long masv);


    @Query(
            value = "SELECT kqht.*, mh.ten_mon, mh.ty_le_diem_qua_trinh " +
                    "FROM ket_qua_hoc_tap kqht " +
                    "JOIN mon_hoc mh ON kqht.ma_mon = mh.ma_mon " +
                    "WHERE kqht.ma_sv = ma_sv",
            nativeQuery = true
    )
    List<KetQuaHocTap> diemMonHocSV(@Param("ma_sv") Long masv);

    @Modifying
    @Query(value = "UPDATE ket_qua_hoc_tap " +
            "SET diem_qua_trinh = :dqt, " +
            "    diem_thanh_phan = :dtp, " +
            "    update_at = NOW() " +
            "WHERE ma_sv = :maSv AND ma_mon = :maMon",
            nativeQuery = true)
    int updateDiemBySinhVienAndMonHoc(
            @Param("maSv") Long maSv,
            @Param("maMon") Long maMon,
            @Param("dqt") Double diemQuaTrinh,
            @Param("dtp") Double diemThanhPhan
    );

    @Query(
            value = "SELECT k.diem_qua_trinh, k.diem_thanh_phan, m.ten_mon, m.ty_le_diem_qua_trinh " +
                    "FROM ket_qua_hoc_tap k " +
                    "JOIN mon_hoc m ON k.ma_mon = m.ma_mon " +
                    "WHERE k.ma_sv = :ma_sv",
            nativeQuery = true
    )
    List<Object[]> findKetQuaBySinhVien(@Param("ma_sv") Long masv);



}

