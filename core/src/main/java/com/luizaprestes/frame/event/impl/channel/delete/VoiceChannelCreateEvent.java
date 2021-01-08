package com.luizaprestes.frame.event.impl.channel.delete;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.channel.VoiceChannel;
import com.luizaprestes.frame.event.impl.channel.common.VoiceChannelEvent;
import lombok.Getter;

@Getter
public class VoiceChannelCreateEvent extends VoiceChannelEvent {

    public VoiceChannelCreateEvent(Frame client, int responseNumber, VoiceChannel voiceChannel) {
        super(client, responseNumber, voiceChannel);
    }

}
