package com.luizaprestes.wrapper.entities.channel;

import com.luizaprestes.wrapper.entities.guild.Guild;
import com.luizaprestes.wrapper.entities.user.User;

import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
public interface Channel {

    String getId();

    String getName();

    Guild getGuild();

    List<User> getUsers();

}