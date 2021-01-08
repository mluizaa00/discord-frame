package com.luizaprestes.frame.entities.channel.impl;

import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.guild.model.Role;
import com.luizaprestes.frame.entities.user.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class TextChannelImpl implements TextChannel {

    private String name;
    private final String id;
    private String topic;

    private int position;

    private final Guild guild;

    private boolean nsfw;

    private List<Role> roles = new ArrayList<>();
    private List<User> users = new ArrayList<>();

}
