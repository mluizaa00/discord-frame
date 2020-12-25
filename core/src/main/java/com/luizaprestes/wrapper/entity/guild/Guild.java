package com.luizaprestes.wrapper.entity.guild;

import com.luizaprestes.wrapper.entity.channel.registry.TextChannelRegistry;
import com.luizaprestes.wrapper.entity.channel.registry.VoiceChannelRegistry;
import com.luizaprestes.wrapper.entity.guild.model.Region;

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
