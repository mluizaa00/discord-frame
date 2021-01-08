package com.luizaprestes.frame.event.channel.delete;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.event.channel.common.TextChannelEvent;
import lombok.Getter;

@Getter
public class TextChannelCreateEvent extends TextChannelEvent {

    public TextChannelCreateEvent(Frame client, int responseNumber, TextChannel textChannel) {
        super(client, responseNumber, textChannel);
    }

}
