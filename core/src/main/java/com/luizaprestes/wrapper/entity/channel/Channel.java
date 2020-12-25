package com.luizaprestes.wrapper.entity.channel;

import com.luizaprestes.wrapper.entity.guild.Guild;
import com.luizaprestes.wrapper.entity.user.User;

import java.util.List;

public interface Channel {

    String getId();

    String getName();

    Guild getGuild();

    List<User> getUsers();

}
