package com.luizaprestes.frame.entities.channel;

import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.user.User;

import java.util.List;

public interface Channel {

    String getId();

    String getName();

    Guild getGuild();

    List<User> getUsers();

}
