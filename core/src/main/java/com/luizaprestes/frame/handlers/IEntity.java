package com.luizaprestes.frame.handlers;

import com.luizaprestes.frame.entities.channel.PrivateChannel;
import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.channel.VoiceChannel;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.guild.Role;
import com.luizaprestes.frame.entities.user.SelfUser;
import com.luizaprestes.frame.entities.user.User;

public interface IEntity {

    Guild createGuild(String context);

    PrivateChannel createPrivateChannel(String context);

    SelfUser createSelfInfo(String context);

    Role createRole(String context, String guildId);

    TextChannel createTextChannel(String context, String guildId);

    VoiceChannel createVoiceChannel(String context, String guildId);

    User createUser(String context);

}
