package com.luizaprestes.wrapper.entities.guild;

import com.luizaprestes.wrapper.entities.channel.TextChannel;
import com.luizaprestes.wrapper.entities.channel.VoiceChannel;
import com.luizaprestes.wrapper.entities.channel.registry.TextChannelRegistry;
import com.luizaprestes.wrapper.entities.channel.registry.VoiceChannelRegistry;
import com.luizaprestes.wrapper.entities.guild.model.Region;
import com.luizaprestes.wrapper.entities.guild.model.Role;

import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
public interface Guild {

    String getId();

    String getName();

    String getIconId();

    String getIconUrl();

    String afkChannelId();

    String getOwnerId();

    int getAfkTimeout();

    Region getRegion();

    TextChannelRegistry getTextChannels();

    VoiceChannelRegistry getVoiceChannels();

    RoleRegistry getRoles();
}
