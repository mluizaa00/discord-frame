package com.luizaprestes.wrapper.entities.user;

import com.luizaprestes.wrapper.entities.channel.PrivateChannel;
import com.luizaprestes.wrapper.entities.user.model.OnlineStatus;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
public interface User {

    String getId();

    String getUsername();

    String getAvatarId();

    String getAvatarUrl();

    OnlineStatus getStatus();

    PrivateChannel getPrivateChannel();

}
