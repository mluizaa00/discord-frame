package com.luizaprestes.wrapper.entities;

import com.luizaprestes.wrapper.entities.channel.PrivateChannel;
import com.luizaprestes.wrapper.entities.channel.TextChannel;
import com.luizaprestes.wrapper.entities.channel.VoiceChannel;
import com.luizaprestes.wrapper.entities.guild.Guild;
import com.luizaprestes.wrapper.entities.guild.impl.GuildImpl;
import com.luizaprestes.wrapper.entities.guild.model.Role;
import com.luizaprestes.wrapper.entities.user.SelfInfo;
import com.luizaprestes.wrapper.entities.user.User;
import org.json.JSONObject;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
public interface IEntity {

    Guild createGuild(JSONObject context);

    PrivateChannel createPrivateChannel(JSONObject context);

    SelfInfo createSelfInfo(JSONObject context);

    Role createRole(JSONObject context, GuildImpl guild);

    TextChannel createTextChannel(JSONObject context, GuildImpl guild);

    VoiceChannel createVoiceChannel(JSONObject context);

    User createUser(JSONObject context);
}