package com.luizaprestes.wrapper.entities.user.impl;

import com.luizaprestes.wrapper.entities.channel.PrivateChannel;
import com.luizaprestes.wrapper.entities.channel.TextChannel;
import com.luizaprestes.wrapper.entities.user.SelfInfo;
import com.luizaprestes.wrapper.entities.user.model.OnlineStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class SelfInfoImpl implements SelfInfo {

    private String id;
    private String username;

    private String avatarId;
    private String avatarURL;

    private OnlineStatus status;

    private PrivateChannel privateChannel;

    private boolean verified;
    private String email;
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
