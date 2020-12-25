package com.luizaprestes.wrapper.entity.channel;

import com.luizaprestes.wrapper.entity.guild.model.Role;

import java.util.List;

public interface TextChannel extends Channel {

    String getTopic();

    boolean isNsfw();

    List<Role> getRoles();

    int getPosition();

}
