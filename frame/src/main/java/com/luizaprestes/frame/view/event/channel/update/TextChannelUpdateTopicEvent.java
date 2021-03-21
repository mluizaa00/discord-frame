package com.luizaprestes.frame.view.event.channel.update;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.channel.TextChannel;
import com.luizaprestes.frame.view.event.channel.common.TextChannelEvent;
import lombok.Getter;

@Getter
public class TextChannelUpdateTopicEvent extends TextChannelEvent {

    private final String oldTopic;

    public TextChannelUpdateTopicEvent(Frame client, int responseNumber, TextChannel textChannel, String oldTopic) {
        super(client, responseNumber, textChannel);

        this.oldTopic = oldTopic;
    }

}
