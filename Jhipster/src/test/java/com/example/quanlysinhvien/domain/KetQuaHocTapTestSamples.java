package com.example.quanlysinhvien.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class KetQuaHocTapTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static KetQuaHocTap getKetQuaHocTapSample1() {
        return new KetQuaHocTap().id(1L);
    }

    public static KetQuaHocTap getKetQuaHocTapSample2() {
        return new KetQuaHocTap().id(2L);
    }

    public static KetQuaHocTap getKetQuaHocTapRandomSampleGenerator() {
        return new KetQuaHocTap().id(longCount.incrementAndGet());
    }
}
