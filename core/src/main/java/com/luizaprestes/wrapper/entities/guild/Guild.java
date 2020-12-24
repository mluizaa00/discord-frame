package com.luizaprestes.wrapper.entities.guild;

import com.luizaprestes.wrapper.entities.channel.registry.TextChannelRegistry;
import com.luizaprestes.wrapper.entities.channel.registry.VoiceChannelRegistry;
import com.luizaprestes.wrapper.entities.guild.model.Region;

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
