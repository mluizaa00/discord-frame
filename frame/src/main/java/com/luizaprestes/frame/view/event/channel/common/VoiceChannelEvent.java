package com.luizaprestes.frame.view.event.channel.common;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.channel.VoiceChannel;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.view.event.Event;
import lombok.Getter;

@Getter
public abstract class VoiceChannelEvent extends Event {

    private final VoiceChannel channel;
    private final Guild guild;

    public VoiceChannelEvent(Frame client, int responseNumber, final VoiceChannel voiceChannel) {
        super(client, responseNumber);

        this.channel = voiceChannel;
        this.guild = channel.getGuild();
    }

}
