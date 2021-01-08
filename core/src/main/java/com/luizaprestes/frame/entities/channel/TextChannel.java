package com.luizaprestes.frame.entities.channel;

import com.luizaprestes.frame.entities.guild.model.Role;

import java.util.List;

public interface TextChannel extends Channel {

    String getTopic();

    boolean isNsfw();

    List<Role> getRoles();

    int getPosition();

}
