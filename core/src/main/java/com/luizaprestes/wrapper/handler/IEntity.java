package com.luizaprestes.wrapper.handler;

import com.luizaprestes.wrapper.entity.channel.PrivateChannel;
import com.luizaprestes.wrapper.entity.channel.TextChannel;
import com.luizaprestes.wrapper.entity.channel.VoiceChannel;
import com.luizaprestes.wrapper.entity.guild.Guild;
import com.luizaprestes.wrapper.entity.guild.impl.GuildImpl;
import com.luizaprestes.wrapper.entity.guild.model.Role;
import com.luizaprestes.wrapper.entity.user.SelfInfo;
import com.luizaprestes.wrapper.entity.user.User;

public interface IEntity {

    Guild createGuild(String context);

    PrivateChannel createPrivateChannel(String context);

    SelfInfo createSelfInfo(String context);

    Role createRole(String context, GuildImpl guild);

    TextChannel createTextChannel(String context, GuildImpl guild);

    VoiceChannel createVoiceChannel(String context, GuildImpl guild);

    User createUser(String context);
}
