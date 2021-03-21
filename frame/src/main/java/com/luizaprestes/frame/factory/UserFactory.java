package com.luizaprestes.frame.factory;

import com.celeste.registries.Registry;
import com.celeste.registries.impl.ConcurrentRegistry;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.controller.UserController;
import com.luizaprestes.frame.model.user.User;
import lombok.Getter;

@Getter
public class UserFactory {

    private final Registry<String, User> registry;
    private final UserController controller;

    public UserFactory(final Frame frame) {
        this.registry = new ConcurrentRegistry<>();
        this.controller = new UserController(frame);
    }

}
