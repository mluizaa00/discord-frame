package com.luizaprestes.frame.model.channel;

import com.celeste.registries.Registry;
import com.celeste.registries.impl.ConcurrentRegistry;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.model.user.User;
import lombok.*;

/**
 @author luiza
 @since 12.19.2020
 */
@Getter
@Setter
public final class PrivateChannel extends Channel {

    private PrivateChannel(final String id, final String name,
                          final Guild guild, final User user
    ) {
        super(id, name, guild);

        this.users = new ConcurrentRegistry<>();
        this.user = user;
    }

    private Registry<String, User> users;
    private User user;

}
