package com.luizaprestes.frame.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.factory.GuildFactory;
import com.luizaprestes.frame.model.Region;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.model.guild.Role;
import com.luizaprestes.frame.util.JacksonAdapter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class GuildController {

    private final Frame frame;
    private final GuildFactory factory;

    public Guild create(final String context) {
        try {
            final JacksonAdapter adapter = frame.getJacksonAdapter();
            final ObjectNode content = adapter.getNode(context);

            if (adapter.getBoolean(content, "unavailable")) return null;

            final String id = adapter.getString(content, "id");
            final Guild guild = factory.getRegistry().get(id);

            guild.setIconId(content.get("icon") != null ? adapter.getString(content, "icon") : null);
            guild.setRegion(Region.getById(adapter.getString(content, "region")));
            guild.setName(adapter.getString(content, "name"));
            guild.setOwnerId(adapter.getString(content, "owner_id"));
            guild.setAfkTimeout(adapter.getInt(content, "afk_timeout"));
            guild.setAfkChannelId(content.get("afk_channel_id")
                    != null ? adapter.getString(content, "afk_channel_id") : null
            );

            final ArrayNode channels = (ArrayNode) content.get("channels");
            for (int i = 0; i < channels.size(); i++) {
                final ObjectNode channel = adapter.getNode(channels.get(i).toPrettyString());
                final String type = adapter.getString(channel, "type");

                if (type == null) break;
                switch (type) {
                    case "text":
                        frame.getChannelController().createTextChannel(context, id);
                    case "voice":
                        frame.getChannelController().createVoiceChannel(context, id);
                }

            }

            final ArrayNode roles = (ArrayNode) content.get("roles");
            for (int i = 0; i < roles.size(); i++) {
                final ObjectNode roleObject = adapter.getNode(roles.get(i).toPrettyString());
                final Role role = frame.getRoleController().create(roleObject.toPrettyString(), id);

                guild.getRoles().register(role.getId(), role);
            }

            final ArrayNode members = (ArrayNode) content.get("members");
            for (int i = 0; i < members.size(); i++) {
                final ObjectNode member = adapter.getNode(members.get(i).get("user").toPrettyString());
                frame.getUserFactory().getController().create(member.toPrettyString());
            }

            factory.getRegistry().register(guild.getId(), guild);
            return guild;
        } catch (Exception exception) {
            frame.getLogger().atSevere().log(
                "A error occurred while creating Guild from Payload message. Value: %s",
                exception.getMessage()
            );
        }

        return null;
    }

}
