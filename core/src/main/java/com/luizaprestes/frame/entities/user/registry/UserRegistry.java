package com.luizaprestes.frame.entities.user.registry;

import com.luizaprestes.frame.entities.user.User;

import java.util.Collection;
import java.util.HashMap;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
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

    public void remove(final String userId) {
        users.remove(userId);
    }

    public Collection<User> getAll() {
        return users.values();
    }
}
