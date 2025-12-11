package com.example.quanlysinhvien.repository;

import com.example.quanlysinhvien.domain.MonHoc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MonHoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonHocRepository extends JpaRepository<MonHoc, Long> {}
