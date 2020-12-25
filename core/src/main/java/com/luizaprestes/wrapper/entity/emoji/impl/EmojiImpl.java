package com.luizaprestes.wrapper.entity.emoji.impl;

import com.luizaprestes.wrapper.entity.emoji.Emoji;
import com.luizaprestes.wrapper.entity.user.User;
import lombok.Data;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class EmojiImpl implements Emoji {

    private final String id;
    private String name;

    private final User creator;

    private boolean animated;

}
