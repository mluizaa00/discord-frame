package com.luizaprestes.wrapper.entities.user;

import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
public interface SelfInfo extends User {

    boolean isVerified();

    String getEmail();

    List<String> getMutedChannelsId();
}
