package com.luizaprestes.frame.view.event.guild;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.model.user.User;
import lombok.Getter;

@Getter
public class GuildJoinEvent extends GuildEvent {

    private final User user;

    public GuildJoinEvent(Frame api, int responseNumber, Guild guild, User user) {
        super(api, responseNumber, guild);

        this.user = user;
    }

}
