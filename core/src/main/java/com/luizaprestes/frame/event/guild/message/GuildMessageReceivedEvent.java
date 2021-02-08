package com.luizaprestes.frame.event.guild.message;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.Message;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.event.guild.GuildEvent;
import lombok.Getter;

@Getter
public class GuildMessageReceivedEvent extends GuildEvent {

    private final Message message;

    public GuildMessageReceivedEvent(Frame api, int responseNumber, Guild guild, Message message) {
        super(api, responseNumber, guild);

        this.message = message;
    }

}
