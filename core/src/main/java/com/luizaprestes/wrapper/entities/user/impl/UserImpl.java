package com.luizaprestes.wrapper.entities.user.impl;

import com.luizaprestes.wrapper.entities.user.User;
import lombok.Data;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Data
public class UserImpl implements User {

    private String id;
    private String username;
    private String avatarId;
    private String avatarUrl;

}
