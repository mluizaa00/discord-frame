package com.luizaprestes.frame.entities.channel;

import com.luizaprestes.frame.entities.Message;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.guild.Role;
import com.luizaprestes.frame.enums.Permission;
import com.luizaprestes.frame.entities.user.User;
import com.luizaprestes.frame.registries.WeakRegistry;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class TextChannel {

    private String name;
    private final String id;
    private String topic;

    private int position;

    private final Guild guild;

    private boolean nsfw;

    private String lastMessageId;

    private WeakRegistry<String, User> users = new WeakRegistry<>();
    private WeakRegistry<String, Role> roles = new WeakRegistry<>();

    public Message sendMessage(@NotNull String message) {
        return null;
    }

    public boolean hasPermission(User user, Permission permission) {
        return false;
    }

}
