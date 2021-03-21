package com.luizaprestes.frame.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.model.guild.Role;
import com.luizaprestes.frame.util.JacksonAdapter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class RoleController {

    private final Frame frame;

    public Role create(final String context, final String guildId) {
        try {
            final JacksonAdapter adapter = frame.getJacksonAdapter();

            final ObjectNode content = adapter.getNode(context);
            final Guild guild = frame.getGuildFactory().getRegistry().get(guildId);

            final String id = adapter.getString(content, "id");
            final Role role = guild.getRoles().get(id);

            role.setName(adapter.getString(content, "name"));
            role.setPosition(adapter.getInt(content, "position"));
            role.setPermissions(adapter.getInt(content, "permissions"));
            role.setManaged(adapter.getBoolean(content, "managed"));
            role.setHoist(adapter.getBoolean(content, "hoist"));
            role.setColor(adapter.getInt(content, "color"));

            guild.getRoles().register(role.getId(), role);
            return role;
        } catch (Exception exception) {
            frame.getLogger().atSevere().log(
                "A error occurred while creating Role from Payload message. Value: %s",
                exception.getMessage()
            );
        }

        return null;
    }

}
