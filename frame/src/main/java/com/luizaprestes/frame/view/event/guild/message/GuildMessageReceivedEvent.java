package com.luizaprestes.frame.view.event.guild.message;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.guild.Message;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.view.event.guild.GuildEvent;
import lombok.Getter;

@Getter
public class GuildMessageReceivedEvent extends GuildEvent {

    private final Message message;

    public GuildMessageReceivedEvent(Frame api, int responseNumber, Guild guild, Message message) {
        super(api, responseNumber, guild);

        this.message = message;
    }

}
