package com.example.quanlysinhvien.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SinhVienTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SinhVien getSinhVienSample1() {
        return new SinhVien().id(1L).tenSv("tenSv1").gioiTinh("gioiTinh1").lop("lop1").khoa("khoa1");
    }

    public static SinhVien getSinhVienSample2() {
        return new SinhVien().id(2L).tenSv("tenSv2").gioiTinh("gioiTinh2").lop("lop2").khoa("khoa2");
    }

    public static SinhVien getSinhVienRandomSampleGenerator() {
        return new SinhVien()
            .id(longCount.incrementAndGet())
            .tenSv(UUID.randomUUID().toString())
            .gioiTinh(UUID.randomUUID().toString())
            .lop(UUID.randomUUID().toString())
            .khoa(UUID.randomUUID().toString());
    }
}
