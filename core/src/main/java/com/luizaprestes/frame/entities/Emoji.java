package com.luizaprestes.frame.entities;

import com.luizaprestes.frame.entities.user.User;
import lombok.Data;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class Emoji {

    private final String id;
    private String name;

    private final User creator;

    private boolean animated;

}
