package com.luizaprestes.frame.entities.user;

import com.luizaprestes.frame.entities.channel.TextChannel;

import java.util.List;

public interface SelfInfo extends User {

    boolean isVerified();

    List<TextChannel> getMutedChannels();

}
