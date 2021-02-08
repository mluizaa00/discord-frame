package com.luizaprestes.frame.entities.guild;

import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.channel.VoiceChannel;
import com.luizaprestes.frame.enums.Region;

import com.luizaprestes.frame.entities.user.User;
import com.luizaprestes.frame.registries.WeakRegistry;
import com.luizaprestes.frame.utils.Constants;
import lombok.Data;

/**
 @author luiza
 @version-implemented 0.0.1
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

    private WeakRegistry<String, TextChannel> textChannels = new WeakRegistry<>();
    private WeakRegistry<String, VoiceChannel> voiceChannels = new WeakRegistry<>();
    private WeakRegistry<String, User> users = new WeakRegistry<>();
    private WeakRegistry<String, Role> roles = new WeakRegistry<>();

    public String getIconId() {
        return Constants.ICONS + getId() + "/" + getIconId() + ".jpg";
    }

}
