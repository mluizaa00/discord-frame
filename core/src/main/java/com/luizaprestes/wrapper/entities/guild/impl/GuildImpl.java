package com.luizaprestes.wrapper.entities.guild.impl;

import com.luizaprestes.wrapper.entities.channel.TextChannel;
import com.luizaprestes.wrapper.entities.channel.VoiceChannel;
import com.luizaprestes.wrapper.entities.guild.Guild;
import com.luizaprestes.wrapper.entities.guild.Region;

import com.luizaprestes.wrapper.util.Constants;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GuildImpl implements Guild {

    private String id;
    private String name;

    private String iconId;
    private String iconUrl;

    private String ownerId;

    private String afkChannelId;
    private int afkTimeout;

    private Region region;

    private List<TextChannel> textChannels = new ArrayList<>();
    private List<VoiceChannel> voiceChannels = new ArrayList<>();

    @Override
    public String getIconId() {
        return Constants.ICONS + getId() + "/" + getIconId() + ".jpg";
    }

    @Override
    public List<TextChannel> getTextChannels() {
        return List.copyOf(textChannels);
    }

    @Override
    public List<VoiceChannel> getVoiceChannels() {
        return List.copyOf(voiceChannels);
    }

    @Override
    public String afkChannelId() {
        return afkChannelId;
    }

}
