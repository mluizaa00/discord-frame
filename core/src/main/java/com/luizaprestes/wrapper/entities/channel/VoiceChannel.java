package com.luizaprestes.wrapper.entities.channel;

import com.luizaprestes.wrapper.entities.guild.Guild;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
public interface VoiceChannel {

    String getId();

    String getName();

    Guild getGuild();

}
