package com.example.quanlysinhvien.repository;

import com.example.quanlysinhvien.domain.KetQuaHocTap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KetQuaHocTap entity.
 */
@Repository
public interface KetQuaHocTapRepository extends JpaRepository<KetQuaHocTap, Long> {
    default Optional<KetQuaHocTap> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<KetQuaHocTap> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<KetQuaHocTap> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "SELECT k.diem_qua_trinh, k.diem_thanh_phan, m.ten_mon, m.ty_le_diem_qua_trinh " +
            "FROM ket_qua_hoc_tap k " +
            "JOIN mon_hoc m ON k.mon_hoc_id = m.id " +
            "WHERE k.sinh_vien_id = :id",
        nativeQuery = true
    )
    List<Object[]> findKetQuaBySinhVien(@Param("id") Long masv);

















    @Query(
        value = "select ketQuaHocTap from KetQuaHocTap ketQuaHocTap left join fetch ketQuaHocTap.sinhVien left join fetch ketQuaHocTap.monHoc",
        countQuery = "select count(ketQuaHocTap) from KetQuaHocTap ketQuaHocTap"
    )
    Page<KetQuaHocTap> findAllWithToOneRelationships(Pageable pageable);

    @Query("select ketQuaHocTap from KetQuaHocTap ketQuaHocTap left join fetch ketQuaHocTap.sinhVien left join fetch ketQuaHocTap.monHoc")
    List<KetQuaHocTap> findAllWithToOneRelationships();

    @Query(
        "select ketQuaHocTap from KetQuaHocTap ketQuaHocTap left join fetch ketQuaHocTap.sinhVien left join fetch ketQuaHocTap.monHoc where ketQuaHocTap.id =:id"
    )
    Optional<KetQuaHocTap> findOneWithToOneRelationships(@Param("id") Long id);
}
