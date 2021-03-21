package com.luizaprestes.frame.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.channel.TextChannel;
import com.luizaprestes.frame.model.channel.VoiceChannel;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.util.JacksonAdapter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ChannelController {

    private final Frame frame;
    private final JacksonAdapter adapter;

    public TextChannel createTextChannel(final String context, final String guildId) {
        try {
            final Guild guild = frame.getGuildFactory().getRegistry().get(guildId);
            final ObjectNode content = adapter.getNode(context);

            final String id = adapter.getString(content, "id");
            final TextChannel channel = guild.getTextChannels().get(id);

            channel.setName(adapter.getString(content, "name"));
            channel.setTopic(adapter.getString(content, "topic"));
            channel.setPosition(adapter.getInt(content, "position"));

            guild.getTextChannels().register(channel.getId(), channel);
            return channel;
        } catch (Exception exception) {
            frame.getLogger().atSevere().log(
                "A error occurred while creating TextChannel from Payload message. Value: %s",
                exception.getMessage()
            );
        }

        return null;
    }

    public VoiceChannel createVoiceChannel(final String context, final String guildId) {
        try {
            final ObjectNode content = adapter.getNode(context);
            final Guild guild = frame.getGuildFactory().getRegistry().get(guildId);

            final String id = adapter.getString(content, "id");
            final VoiceChannel channel = guild.getVoiceChannels().get(id);

            channel.setName(adapter.getString(content, "name"));
            channel.setUserLimit(adapter.getInt(content, "user_limit"));
            channel.setBitrate(adapter.getInt(content, "bitrate"));

            guild.getVoiceChannels().register(channel.getId(), channel);
            return channel;
        } catch (Exception exception) {
            frame.getLogger().atSevere().log(
                    "A error occurred while creating VoiceChannel from Payload message. Value: %s",
                    exception.getMessage()
            );
        }

        return null;
    }

}
