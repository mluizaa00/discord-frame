package com.luizaprestes.frame.view.event.guild;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.view.event.Event;
import lombok.Getter;

@Getter
public abstract class GuildEvent extends Event {

    private final Guild guild;

    public GuildEvent(Frame api, int responseNumber, Guild guild) {
        super(api, responseNumber);
        this.guild = guild;
    }

}
