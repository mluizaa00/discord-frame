package com.luizaprestes.frame.view.event.channel.create;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.channel.VoiceChannel;
import com.luizaprestes.frame.view.event.channel.common.VoiceChannelEvent;
import lombok.Getter;

@Getter
public class VoiceChannelCreateEvent extends VoiceChannelEvent {

    public VoiceChannelCreateEvent(Frame client, int responseNumber, VoiceChannel voiceChannel) {
        super(client, responseNumber, voiceChannel);
    }

}
