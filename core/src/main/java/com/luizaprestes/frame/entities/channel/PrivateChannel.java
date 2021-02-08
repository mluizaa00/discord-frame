package com.luizaprestes.frame.entities.channel;

import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.user.User;
import com.luizaprestes.frame.registries.WeakRegistry;
import lombok.Data;

import java.util.UUID;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class PrivateChannel {

    private final UUID id;

    private final String name;
    private final Guild guild;

    private WeakRegistry<String, User> users = new WeakRegistry<>();

    private User user;

}
