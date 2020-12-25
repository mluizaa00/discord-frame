package com.luizaprestes.wrapper.entity.user;

import com.luizaprestes.wrapper.entity.channel.PrivateChannel;
import com.luizaprestes.wrapper.entity.user.model.OnlineStatus;

public interface User {

    String getId();

    String getUsername();

    String getAvatarId();

    String getAvatarURL();

    OnlineStatus getStatus();

    PrivateChannel getPrivateChannel();

}
