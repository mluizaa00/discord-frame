package com.luizaprestes.frame.event.channel.common;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.event.Event;
import lombok.Getter;

@Getter
public abstract class TextChannelEvent extends Event {

    private final TextChannel channel;
    private final Guild guild;

    public TextChannelEvent(Frame client, int responseNumber, final TextChannel textChannel) {
        super(client, responseNumber);

        this.channel = textChannel;
        this.guild = channel.getGuild();
    }

}
