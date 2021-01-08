package com.luizaprestes.frame.event.impl.channel.update;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.event.impl.channel.common.TextChannelEvent;
import lombok.Getter;

@Getter
public class TextChannelUpdateTopicEvent extends TextChannelEvent {

    private final String oldTopic;

    public TextChannelUpdateTopicEvent(Frame client, int responseNumber, TextChannel textChannel, String oldTopic) {
        super(client, responseNumber, textChannel);

        this.oldTopic = oldTopic;
    }

}
