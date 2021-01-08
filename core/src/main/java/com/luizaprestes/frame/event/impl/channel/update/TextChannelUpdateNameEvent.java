package com.luizaprestes.frame.event.impl.channel.update;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.event.impl.channel.common.TextChannelEvent;
import lombok.Getter;

@Getter
public class TextChannelUpdateNameEvent extends TextChannelEvent {

    private final String oldName;

    public TextChannelUpdateNameEvent(Frame client, int responseNumber, TextChannel textChannel, String oldName) {
        super(client, responseNumber, textChannel);

        this.oldName = oldName;
    }

}
