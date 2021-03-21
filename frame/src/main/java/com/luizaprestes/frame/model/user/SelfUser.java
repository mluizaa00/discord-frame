package com.luizaprestes.frame.model.user;

import com.luizaprestes.frame.model.channel.PrivateChannel;
import com.luizaprestes.frame.model.channel.TextChannel;
import com.luizaprestes.frame.model.OnlineStatus;
import lombok.Data;

import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class SelfUser {

    private String id;
    private String username;

    private String avatarId;
    private String avatarURL;

    private OnlineStatus status;

    private PrivateChannel privateChannel;

    private boolean verified;
    private List<TextChannel> mutedChannels;

}
