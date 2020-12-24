package com.luizaprestes.wrapper.entities.emoji;

import com.luizaprestes.wrapper.entities.user.User;

public interface Emoji {

    String getId();

    String getName();

    User getCreator();

    boolean isAnimated();
}
