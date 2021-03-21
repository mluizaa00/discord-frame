package com.luizaprestes.frame.view.event.guild.role;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.model.guild.Role;
import com.luizaprestes.frame.view.event.guild.GuildEvent;
import lombok.Getter;

@Getter
public class GuildRoleDeleteEvent extends GuildEvent {

    private final Role deletedRole;

    public GuildRoleDeleteEvent(Frame api, int responseNumber, Guild guild, Role deletedRole) {
        super(api, responseNumber, guild);

        this.deletedRole = deletedRole;
    }

}
