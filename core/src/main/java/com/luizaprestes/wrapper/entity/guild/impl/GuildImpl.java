package com.luizaprestes.wrapper.entity.guild.impl;

import com.luizaprestes.wrapper.entity.channel.TextChannel;
import com.luizaprestes.wrapper.entity.channel.VoiceChannel;
import com.luizaprestes.wrapper.entity.channel.registry.TextChannelRegistry;
import com.luizaprestes.wrapper.entity.channel.registry.VoiceChannelRegistry;
import com.luizaprestes.wrapper.entity.guild.Guild;
import com.luizaprestes.wrapper.entity.guild.RoleRegistry;
import com.luizaprestes.wrapper.entity.guild.model.Region;

import com.luizaprestes.wrapper.entity.guild.model.Role;
import com.luizaprestes.wrapper.util.Constants;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class GuildImpl implements Guild {

    private final String id;
    private String name;

    private String iconId;
    private String iconUrl;

    private String ownerId;

    private String afkChannelId;
    private int afkTimeout;

    private Region region;

    private TextChannelRegistry textChannels = new TextChannelRegistry();
    private VoiceChannelRegistry voiceChannels = new VoiceChannelRegistry();
    private RoleRegistry roles = new RoleRegistry();

    @Override
    public String getIconId() {
        return Constants.ICONS + getId() + "/" + getIconId() + ".jpg";
    }

    @Override
    public String afkChannelId() {
        return afkChannelId;
    }

    public List<TextChannel> getTextList() {
        return new ArrayList<>(textChannels.getAll());
    }

    public List<VoiceChannel> getVoiceList() {
        return new ArrayList<>(voiceChannels.getAll());
    }

    public List<Role> getRolesList() {
        return new ArrayList<>(roles.getAll());
    }

}
