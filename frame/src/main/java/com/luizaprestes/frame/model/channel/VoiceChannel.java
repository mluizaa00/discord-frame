package com.luizaprestes.frame.model.channel;

import com.luizaprestes.frame.model.guild.Guild;
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
public final class VoiceChannel extends Channel {

    private VoiceChannel(final String id, final String name,
                         final Guild guild, final int userLimit, final int bitrate
    ) {
        super(id, name, guild);

        this.userLimit = userLimit;
        this.bitrate = bitrate;
        this.users = new LinkedList<>();
    }

    private int userLimit;
    private int bitrate;

    private List<User> users;

}
