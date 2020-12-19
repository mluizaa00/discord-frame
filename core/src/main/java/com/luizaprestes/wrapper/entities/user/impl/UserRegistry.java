package com.luizaprestes.wrapper.entities.user.impl;

import com.luizaprestes.wrapper.entities.user.User;

import java.util.Collection;
import java.util.HashMap;

public class UserRegistry {

    private final HashMap<String, User> users;

    public UserRegistry() {
        this.users = new HashMap<>();
    }

    public void registerUser(final User user) {
        users.put(user.getId(), user);
    }

    public User getUserById(final String userId) {
        return users.get(userId);
    }

    public void cleanup(final String userId) {
        users.remove(userId);
    }

    public Collection<User> getAll() {
        return users.values();
    }
}
