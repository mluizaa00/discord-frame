package com.luizaprestes.wrapper.entities.user;

import com.luizaprestes.wrapper.entities.channel.PrivateChannel;
import com.luizaprestes.wrapper.entities.user.model.OnlineStatus;

public interface User {

    String getId();

    String getUsername();

    String getAvatarId();

    String getAvatarURL();

    OnlineStatus getStatus();

    PrivateChannel getPrivateChannel();

}
