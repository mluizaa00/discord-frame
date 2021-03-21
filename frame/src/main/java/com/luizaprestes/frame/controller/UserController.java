package com.luizaprestes.frame.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.user.User;
import com.luizaprestes.frame.util.JacksonAdapter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class UserController {

    private final Frame frame;

    public User create(final String context) {
        final JacksonAdapter adapter = frame.getJacksonAdapter();

        final ObjectNode content = adapter.getNode(context);
        final String id = adapter.getString(content, "id");

        final User user = frame.getUserFactory().getRegistry().get(id);

        user.setUsername(adapter.getString(content, "username"));
        user.setAvatarId(adapter.getString(content, "avatar"));

        frame.getUserFactory().getRegistry().register(user.getId(), user);
        return user;
    }

}
