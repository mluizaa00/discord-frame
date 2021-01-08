package com.luizaprestes.frame.entities.channel.registry;

import com.luizaprestes.frame.entities.channel.TextChannel;

import java.util.Collection;
import java.util.HashMap;

public class TextChannelRegistry {

    private final HashMap<String, TextChannel> textChannels;

    public TextChannelRegistry() {
        this.textChannels = new HashMap<>();
    }

    public void registerChannel(final TextChannel channel) {
        textChannels.put(channel.getId(), channel);
    }

    public TextChannel getChannelById(final String channelId) {
        return textChannels.get(channelId);
    }

    public void remove(final String channelId) {
        textChannels.remove(channelId);
    }

    public Collection<TextChannel> getAll() {
        return textChannels.values();
    }

}
