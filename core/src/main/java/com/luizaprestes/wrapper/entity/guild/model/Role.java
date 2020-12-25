package com.luizaprestes.wrapper.entity.guild.model;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
public interface Role {

    String getId();

    String getName();

    int getColor();

    boolean isHoist();

    int getPosition();

    boolean hasPermission(Permission permission);

    boolean isManaged();

    boolean isMentionable();

}
