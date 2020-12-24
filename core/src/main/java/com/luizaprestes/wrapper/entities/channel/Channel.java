package com.luizaprestes.wrapper.entities.channel;

import com.luizaprestes.wrapper.entities.guild.Guild;
import com.luizaprestes.wrapper.entities.user.User;

import java.util.List;

public interface Channel {

    String getId();

    String getName();

    Guild getGuild();

    List<User> getUsers();

}
