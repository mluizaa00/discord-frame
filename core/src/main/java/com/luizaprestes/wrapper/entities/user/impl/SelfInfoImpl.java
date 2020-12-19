package com.luizaprestes.wrapper.entities.user.impl;

import com.luizaprestes.wrapper.entities.channel.Channel;
import com.luizaprestes.wrapper.entities.user.SelfInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SelfInfoImpl extends UserImpl implements SelfInfo {

    private boolean verified;
    private String email;
    private List<Channel> mutedChannels;

    @Override
    public List<Channel> getMutedChannels() {
        return List.copyOf(mutedChannels);
    }

    public List<Channel> getModifiableMutedChannels() {
        if (mutedChannels == null)  {
            mutedChannels = new ArrayList<>();
        }

        return mutedChannels;
    }

}
