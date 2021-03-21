package com.luizaprestes.frame.model.user;

import com.luizaprestes.frame.model.channel.PrivateChannel;
import com.luizaprestes.frame.model.OnlineStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
@RequiredArgsConstructor
public class User {

    private final String id;
    private String username;

    private boolean bot;

    private String avatarId;
    private String avatarURL;

    private OnlineStatus status = OnlineStatus.ONLINE;
    private PrivateChannel privateChannel = null;

}
