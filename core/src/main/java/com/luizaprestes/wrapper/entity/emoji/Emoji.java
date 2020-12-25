package com.luizaprestes.wrapper.entity.emoji;

import com.luizaprestes.wrapper.entity.user.User;

public interface Emoji {

    String getId();

    String getName();

    User getCreator();

    boolean isAnimated();
}
