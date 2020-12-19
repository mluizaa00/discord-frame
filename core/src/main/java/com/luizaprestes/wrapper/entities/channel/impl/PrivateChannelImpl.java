package com.luizaprestes.wrapper.entities.channel.impl;

import com.luizaprestes.wrapper.entities.channel.PrivateChannel;
import com.luizaprestes.wrapper.entities.user.User;
import lombok.Data;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class PrivateChannelImpl implements PrivateChannel {

    private final String id;
    private User user;

}
