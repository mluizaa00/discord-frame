package com.luizaprestes.frame.entities.user;

import com.luizaprestes.frame.entities.channel.VoiceChannel;
import com.luizaprestes.frame.entities.guild.Guild;

public interface VoiceStatus {

    boolean isMuted();

    boolean isServerMuted();

    VoiceChannel getChannel();

    Guild getGuild();

    User getUser();

}
