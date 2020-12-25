package com.luizaprestes.wrapper.entity.user;

import com.luizaprestes.wrapper.entity.channel.TextChannel;

import java.util.List;

public interface SelfInfo extends User {

    boolean isVerified();

    List<TextChannel> getMutedChannels();

}
