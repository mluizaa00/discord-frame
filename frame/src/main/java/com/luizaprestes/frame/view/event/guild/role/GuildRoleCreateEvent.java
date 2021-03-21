package com.luizaprestes.frame.view.event.guild.role;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.view.event.guild.GuildEvent;
import lombok.Getter;
import org.java_websocket.enums.Role;

@Getter
public class GuildRoleCreateEvent extends GuildEvent {

    private final Role createdRole;

    public GuildRoleCreateEvent(Frame api, int responseNumber, Guild guild, Role createdRole) {
        super(api, responseNumber, guild);

        this.createdRole = createdRole;
    }

}
