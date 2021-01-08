package com.luizaprestes.frame.event.guild;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.event.Event;
import lombok.Getter;

@Getter
public abstract class GuildEvent extends Event {

    private final Guild guild;

    public GuildEvent(Frame api, int responseNumber, Guild guild) {
        super(api, responseNumber);
        this.guild = guild;
    }

}
