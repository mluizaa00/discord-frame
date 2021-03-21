package com.luizaprestes.frame.model.channel;

import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.model.guild.Role;
import com.luizaprestes.frame.model.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 @author luiza
 @since 12.19.2020
 */
@Getter
@Setter
public final class TextChannel extends Channel {

    private TextChannel(final String id, final String name, final Guild guild,
                        final int position, final boolean nsfw, final String lastMessageId
    ) {
        super(id, name, guild);

        this.topic = "";
        this.position = position;
        this.nsfw = nsfw;
        this.lastMessageId = lastMessageId;

        this.users = new LinkedList<>();
        this.roles = new LinkedList<>();
    }

    private String topic;

    private int position;
    private boolean nsfw;

    private String lastMessageId;

    private List<User> users;
    private List<Role> roles;

}
