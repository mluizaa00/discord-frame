package com.luizaprestes.frame.event.guild.role;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.guild.Role;
import com.luizaprestes.frame.event.guild.GuildEvent;
import lombok.Getter;

@Getter
public class GuildRoleDeleteEvent extends GuildEvent {

    private final Role deletedRole;

    public GuildRoleDeleteEvent(Frame api, int responseNumber, Guild guild, Role deletedRole) {
        super(api, responseNumber, guild);

        this.deletedRole = deletedRole;
    }

}
