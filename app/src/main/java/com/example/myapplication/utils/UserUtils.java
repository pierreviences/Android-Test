package com.example.myapplication.utils;

import com.example.myapplication.data.model.User;

public class UserUtils {

    public static User[] getUsers() {
        return new User[]{
                new User("a", "a"),
                new User("admin", "admin"),
                new User("b", "b")
        };
    }
}
