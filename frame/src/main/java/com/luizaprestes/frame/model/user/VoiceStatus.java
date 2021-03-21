package com.luizaprestes.frame.model.user;

import com.luizaprestes.frame.model.channel.VoiceChannel;
import com.luizaprestes.frame.model.guild.Guild;

public interface VoiceStatus {

    boolean isMuted();

    boolean isServerMuted();

    VoiceChannel getChannel();

    Guild getGuild();

    User getUser();

}
