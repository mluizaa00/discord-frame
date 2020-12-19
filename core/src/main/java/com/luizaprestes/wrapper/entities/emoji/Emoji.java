package com.luizaprestes.wrapper.entities.emoji;

import com.luizaprestes.wrapper.entities.user.User;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
public interface Emoji {

    String getId();

    String getName();

    User getCreator();

    boolean isAnimated();
}
