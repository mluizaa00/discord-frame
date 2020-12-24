package com.luizaprestes.wrapper.entities.user.model;

import lombok.AllArgsConstructor;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
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
