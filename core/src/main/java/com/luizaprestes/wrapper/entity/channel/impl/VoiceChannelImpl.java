package com.luizaprestes.wrapper.entity.channel.impl;

import com.luizaprestes.wrapper.entity.channel.VoiceChannel;
import com.luizaprestes.wrapper.entity.guild.Guild;
import com.luizaprestes.wrapper.entity.user.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class VoiceChannelImpl implements VoiceChannel {

    private String name;
    private final String id;

    private final Guild guild;

    private int userLimit;
    private int bitrate;

    private List<User> users = new ArrayList<>();

}
