package com.example.quanlysinhvien.domain;

import static com.example.quanlysinhvien.domain.MonHocTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.quanlysinhvien.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MonHocTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonHoc.class);
        MonHoc monHoc1 = getMonHocSample1();
        MonHoc monHoc2 = new MonHoc();
        assertThat(monHoc1).isNotEqualTo(monHoc2);

        monHoc2.setId(monHoc1.getId());
        assertThat(monHoc1).isEqualTo(monHoc2);

        monHoc2 = getMonHocSample2();
        assertThat(monHoc1).isNotEqualTo(monHoc2);
    }
}
