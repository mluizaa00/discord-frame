package com.luizaprestes.wrapper.entities.user;

import com.luizaprestes.wrapper.entities.channel.TextChannel;

import java.util.List;

public interface SelfInfo extends User {

    boolean isVerified();

    List<TextChannel> getMutedChannels();

}
