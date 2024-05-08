package com.kn.initialmusic.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class cookieUtil {

    // 默认缓存时间,单位/秒, 7day
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 7;
    // 保存路径,根路径
    private static final String COOKIE_PATH = "/";

    /**
     * 保存
     *
     * @param response   响应体
     * @param key        cookie的键（或者是name）
     * @param value      对于key的值
     * @param ifRemember 是否记住cookie
     */
    public static void set(HttpServletResponse response, String key, String value, boolean ifRemember) {
        int age = ifRemember ? COOKIE_MAX_AGE : -1;
        set(response, key, value, null, COOKIE_PATH, age, true);
    }

    /**
     * 保存
     *
     * @param response 响应体
     * @param key      cookie的键（或者是name）
     * @param value    对于key的值
     * @param maxAge   cookie缓存时间
     */
    private static void set(HttpServletResponse response, String key, String value, String domain, String path, int maxAge, boolean isHttpOnly) {
        Cookie cookie = new Cookie(key, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(isHttpOnly);
        response.addCookie(cookie);
    }

    /**
     * 查询value
     *
     * @param request 请求体
     * @param key     待查询cookie的键
     * @return 对于cookie的值
     */
    public static String getValue(HttpServletRequest request, String key) {
        Cookie cookie = get(request, key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 查询Cookie
     *
     * @param request 请求体
     * @param key     待查询cookie的键
     */
    private static Cookie get(HttpServletRequest request, String key) {
        Cookie[] arr_cookie = request.getCookies();
        if (arr_cookie != null && arr_cookie.length > 0) {
            for (Cookie cookie : arr_cookie) {
                if (cookie.getName().equals(key)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 删除Cookie
     *
     * @param request  请求体
     * @param response 响应体
     * @param key      待删除cookie的键
     */
    public static void remove(HttpServletRequest request, HttpServletResponse response, String key) {
        Cookie cookie = get(request, key);
        if (cookie != null) {
            set(response, key, "", null, COOKIE_PATH, 0, true);
        }
    }

}