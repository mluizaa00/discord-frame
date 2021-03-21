package com.luizaprestes.frame.model.guild;

import com.celeste.registries.Registry;
import com.celeste.registries.impl.ConcurrentRegistry;
import com.luizaprestes.frame.model.channel.TextChannel;
import com.luizaprestes.frame.model.channel.VoiceChannel;
import com.luizaprestes.frame.model.Region;

import com.luizaprestes.frame.model.user.User;
import com.luizaprestes.frame.util.Constants;
import lombok.Data;

/**
 @author luiza
 @since 12.19.2020
 */
@Data
public class Guild {

    private final String id;
    private String name;

    private String iconId;
    private String iconUrl;

    private String ownerId;

    private String afkChannelId;
    private int afkTimeout;

    private Region region;

    private Registry<String, TextChannel> textChannels = new ConcurrentRegistry<>();
    private Registry<String, VoiceChannel> voiceChannels = new ConcurrentRegistry<>();
    private Registry<String, User> users = new ConcurrentRegistry<>();
    private Registry<String, Role> roles = new ConcurrentRegistry<>();

    public String getIconId() {
        return Constants.ICONS + getId() + "/" + getIconId() + ".jpg";
    }

}
