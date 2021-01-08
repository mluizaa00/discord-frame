package com.luizaprestes.frame.entities.emoji;

import com.luizaprestes.frame.entities.user.User;

public interface Emoji {

    String getId();

    String getName();

    User getCreator();

    boolean isAnimated();
}
