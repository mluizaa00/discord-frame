package com.luizaprestes.frame.entities.user.impl;

import com.luizaprestes.frame.entities.channel.PrivateChannel;
import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.user.SelfUser;
import com.luizaprestes.frame.entities.user.model.OnlineStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class SelfUserImpl implements SelfUser {

    private String id;
    private String username;

    private String avatarId;
    private String avatarURL;

    private OnlineStatus status;

    private PrivateChannel privateChannel;

    private boolean verified;
    private List<TextChannel> mutedChannels;

    @Override
    public List<TextChannel> getMutedChannels() {
        return List.copyOf(mutedChannels);
    }

    public List<TextChannel> getModifiableMutedChannels() {
        if (mutedChannels == null)  {
            mutedChannels = new ArrayList<>();
        }

        return mutedChannels;
    }

}
