package com.kn.initialmusic.webSocket.pojo;

import jakarta.websocket.Session;

import java.util.Map;

public class MessageHolder {
    private static final ThreadLocal<Map<String, Session>> t1 = new ThreadLocal<>();

    public static void saveSession(Map<String, Session> map) {
        t1.set(map);
    }

    public static Map<String, Session> getSession() {
        return t1.get();
    }

    public static void removeSession() {
        t1.remove();
    }
}
