package com.example.quanlysinhvien.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MonHocTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MonHoc getMonHocSample1() {
        return new MonHoc().id(1L).tenMon("tenMon1");
    }

    public static MonHoc getMonHocSample2() {
        return new MonHoc().id(2L).tenMon("tenMon2");
    }

    public static MonHoc getMonHocRandomSampleGenerator() {
        return new MonHoc().id(longCount.incrementAndGet()).tenMon(UUID.randomUUID().toString());
    }
}
