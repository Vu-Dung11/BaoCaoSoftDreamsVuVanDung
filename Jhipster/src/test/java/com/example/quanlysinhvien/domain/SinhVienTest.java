package com.example.quanlysinhvien.domain;

import static com.example.quanlysinhvien.domain.SinhVienTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.quanlysinhvien.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SinhVienTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SinhVien.class);
        SinhVien sinhVien1 = getSinhVienSample1();
        SinhVien sinhVien2 = new SinhVien();
        assertThat(sinhVien1).isNotEqualTo(sinhVien2);

        sinhVien2.setId(sinhVien1.getId());
        assertThat(sinhVien1).isEqualTo(sinhVien2);

        sinhVien2 = getSinhVienSample2();
        assertThat(sinhVien1).isNotEqualTo(sinhVien2);
    }
}
