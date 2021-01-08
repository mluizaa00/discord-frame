package com.luizaprestes.frame.entities.guild.impl;

import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.channel.VoiceChannel;
import com.luizaprestes.frame.entities.channel.registry.TextChannelRegistry;
import com.luizaprestes.frame.entities.channel.registry.VoiceChannelRegistry;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.guild.registry.RoleRegistry;
import com.luizaprestes.frame.entities.guild.model.Region;

import com.luizaprestes.frame.entities.guild.model.Role;
import com.luizaprestes.frame.utils.Constants;
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
