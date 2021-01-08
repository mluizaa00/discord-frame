package com.luizaprestes.frame.entities.guild;

import com.luizaprestes.frame.entities.channel.registry.TextChannelRegistry;
import com.luizaprestes.frame.entities.channel.registry.VoiceChannelRegistry;
import com.luizaprestes.frame.entities.guild.model.Region;
import com.luizaprestes.frame.entities.guild.registry.RoleRegistry;

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
