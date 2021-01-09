package com.luizaprestes.frame.entities.channel;

import com.luizaprestes.frame.entities.guild.model.Role;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TextChannel extends Channel {

    String getTopic();

    boolean isNsfw();

    List<Role> getRoles();

    int getPosition();

    default void sendMessage(@NotNull String message) {
        // TODO
    }

}
