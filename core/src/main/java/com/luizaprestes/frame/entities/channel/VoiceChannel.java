package com.luizaprestes.frame.entities.channel;

import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.user.User;
import com.luizaprestes.frame.registries.WeakRegistry;
import lombok.Data;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class VoiceChannel {

    private String name;
    private final String id;

    private final Guild guild;

    private int userLimit;
    private int bitrate;

    private WeakRegistry<String, User> userRegistry;

}
