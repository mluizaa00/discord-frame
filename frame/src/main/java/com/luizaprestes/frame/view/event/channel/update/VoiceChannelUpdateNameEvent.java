package com.luizaprestes.frame.view.event.channel.update;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.channel.TextChannel;
import com.luizaprestes.frame.view.event.channel.common.TextChannelEvent;
import lombok.Getter;

@Getter
public class VoiceChannelUpdateNameEvent extends TextChannelEvent {

    private final String oldName;

    public VoiceChannelUpdateNameEvent(Frame client, int responseNumber, TextChannel textChannel, String oldName) {
        super(client, responseNumber, textChannel);

        this.oldName = oldName;
    }

}
