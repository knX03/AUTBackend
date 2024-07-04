package com.kn.initialmusic.util;

public class RedisConstants {
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final String LOGIN_SINGER_KEY = "login-singer:token:";
    public static final String CACHE_USER_KEY = "cache_user:ID:";
    public static final String CACHE_SP_KEY = "cache_sp:ID:";
    public static final String CACHE_SPALL_KEY = "cache_spALL:";
    public static final String CACHE_USERSP_KEY = "cache_userSP:ID:";
    public static final String CACHE_USERLISP_KEY = "cache_userLISP:ID:";
    public static final String CACHE_SP_SONG_KEY = "cache_sp_song:ID:";
    public static final String CACHE_ALALL_KEY = "cache_alALL:";
    public static final String CACHE_PLTAG_KEY = "cache_plTag:";

    public static final Long LOGIN_CODE_TTL = 5L;
    public static final Long LOGIN_USER_TTL = 7L;
    public static final Long LOGIN_SINGER_TTL = 7L;
    public static final Long CACHE_USER_KEY_TTL = 60L;
    public static final Long CACHE_SP_KEY_TTL = 60L;
    public static final Long CACHE_USERSP_KEY_TTL = 60L;
}
