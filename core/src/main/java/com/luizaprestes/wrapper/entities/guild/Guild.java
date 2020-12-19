package com.luizaprestes.wrapper.entities.guild;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
public interface Guild {

    String getId();

    String getName();

    String getIconId();

    String getIconUrl();

    String afkChannelId();

    String getOwnerId();

    int getAfkTimeout();

    Region getRegion();
}
