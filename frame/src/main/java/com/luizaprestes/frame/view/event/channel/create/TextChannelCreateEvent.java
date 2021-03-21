package com.luizaprestes.frame.view.event.channel.create;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.channel.TextChannel;
import com.luizaprestes.frame.view.event.channel.common.TextChannelEvent;
import lombok.Getter;

@Getter
public class TextChannelCreateEvent extends TextChannelEvent {

    public TextChannelCreateEvent(Frame client, int responseNumber, TextChannel textChannel) {
        super(client, responseNumber, textChannel);
    }

}
