package com.kn.initialmusic.util;

import com.kn.initialmusic.pojo.User;

public class UserHolder {
    private static final ThreadLocal<User> t1 = new ThreadLocal<>();

    public static void saveUser(User user) {
        t1.set(user);
    }

    public static User getUser() {
        return t1.get();
    }

    public static void removeUser() {
        t1.remove();
    }
}
