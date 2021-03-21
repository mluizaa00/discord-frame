package com.luizaprestes.frame.model.channel;

import com.luizaprestes.frame.model.Permission;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.model.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public abstract class Channel {

    private final String id;

    private String name;
    private final Guild guild;

    public boolean hasPermission(final User user, final Permission permission) {
        return false;
    }

}
