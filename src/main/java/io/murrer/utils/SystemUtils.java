package io.murrer.utils;

import io.murrer.model.User;

public class SystemUtils {

    public User getUser() {
        return new User(System.getProperty("user.name"), System.getProperty("user.home"));
    }

}
