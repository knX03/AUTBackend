package com.kn.initialmusic.util;

import com.kn.initialmusic.pojo.Singer;


public class SingerHolder {
    private static final ThreadLocal<Singer> t1 = new ThreadLocal<>();

    public static void saveSinger(Singer singer) {
        t1.set(singer);
    }

    public static Singer getSinger() {
        return t1.get();
    }

    public static void removeSinger() {
        t1.remove();
    }
}
