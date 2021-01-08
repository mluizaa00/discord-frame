package com.luizaprestes.frame.entities.user;

import com.luizaprestes.frame.entities.channel.PrivateChannel;
import com.luizaprestes.frame.entities.user.model.OnlineStatus;

public interface User {

    String getId();

    String getUsername();

    String getAvatarId();

    String getAvatarURL();

    OnlineStatus getStatus();

    PrivateChannel getPrivateChannel();

}
