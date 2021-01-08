package com.luizaprestes.frame.entities.channel.registry;

import com.luizaprestes.frame.entities.channel.VoiceChannel;

import java.util.Collection;
import java.util.HashMap;

public class VoiceChannelRegistry {

    private final HashMap<String, VoiceChannel> voiceChannels;

    public VoiceChannelRegistry() {
        this.voiceChannels = new HashMap<>();
    }

    public void registerChannel(final VoiceChannel channel) {
        voiceChannels.put(channel.getId(), channel);
    }

    public VoiceChannel getChannelById(final String channelId) {
        return voiceChannels.get(channelId);
    }

    public void remove(final String channelId) {
        voiceChannels.remove(channelId);
    }

    public Collection<VoiceChannel> getAll() {
        return voiceChannels.values();
    }

}
