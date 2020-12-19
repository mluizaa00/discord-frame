package com.luizaprestes.wrapper.entities.channel;

import com.luizaprestes.wrapper.entities.guild.model.Role;

import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
public interface TextChannel extends Channel {

    String getTopic();

    boolean isNsfw();

    List<Role> getRoles();

    int getPosition();

}
