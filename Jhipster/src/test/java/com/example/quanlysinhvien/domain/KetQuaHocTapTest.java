package com.example.quanlysinhvien.domain;

import static com.example.quanlysinhvien.domain.KetQuaHocTapTestSamples.*;
import static com.example.quanlysinhvien.domain.MonHocTestSamples.*;
import static com.example.quanlysinhvien.domain.SinhVienTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.quanlysinhvien.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KetQuaHocTapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KetQuaHocTap.class);
        KetQuaHocTap ketQuaHocTap1 = getKetQuaHocTapSample1();
        KetQuaHocTap ketQuaHocTap2 = new KetQuaHocTap();
        assertThat(ketQuaHocTap1).isNotEqualTo(ketQuaHocTap2);

        ketQuaHocTap2.setId(ketQuaHocTap1.getId());
        assertThat(ketQuaHocTap1).isEqualTo(ketQuaHocTap2);

        ketQuaHocTap2 = getKetQuaHocTapSample2();
        assertThat(ketQuaHocTap1).isNotEqualTo(ketQuaHocTap2);
    }

    @Test
    void sinhVienTest() {
        KetQuaHocTap ketQuaHocTap = getKetQuaHocTapRandomSampleGenerator();
        SinhVien sinhVienBack = getSinhVienRandomSampleGenerator();

        ketQuaHocTap.setSinhVien(sinhVienBack);
        assertThat(ketQuaHocTap.getSinhVien()).isEqualTo(sinhVienBack);

        ketQuaHocTap.sinhVien(null);
        assertThat(ketQuaHocTap.getSinhVien()).isNull();
    }

    @Test
    void monHocTest() {
        KetQuaHocTap ketQuaHocTap = getKetQuaHocTapRandomSampleGenerator();
        MonHoc monHocBack = getMonHocRandomSampleGenerator();

        ketQuaHocTap.setMonHoc(monHocBack);
        assertThat(ketQuaHocTap.getMonHoc()).isEqualTo(monHocBack);

        ketQuaHocTap.monHoc(null);
        assertThat(ketQuaHocTap.getMonHoc()).isNull();
    }
}
