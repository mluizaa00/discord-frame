package com.luizaprestes.wrapper.entity.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Getter
@AllArgsConstructor
public enum OnlineStatus {

    ONLINE("online"),
    DO_NOT_DISTURB("dnd"),
    AWAY("idle"),
    INVISIBLE("invisible"),
    OFFLINE("offline");

    private final String key;

    public static OnlineStatus fromKey(String key) {
        for (OnlineStatus onlineStatus : values()) {
            if (onlineStatus.key.equalsIgnoreCase(key)) {
                return onlineStatus;
            }
        }
        return null;
    }

}
