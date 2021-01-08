package com.luizaprestes.frame.event.guild.role;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.guild.model.Role;
import com.luizaprestes.frame.event.guild.GuildEvent;
import lombok.Getter;

@Getter
public class GuildRoleCreateEvent extends GuildEvent {

    private final Role createdRole;

    public GuildRoleCreateEvent(Frame api, int responseNumber, Guild guild, Role createdRole) {
        super(api, responseNumber, guild);

        this.createdRole = createdRole;
    }

}
