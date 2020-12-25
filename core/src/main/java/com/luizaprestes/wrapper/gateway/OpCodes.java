package com.luizaprestes.wrapper.gateway;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OpCodes {

    DISPATCH(0),
    HEARTBEAT(1),
    IDENTIFY(2),
    PRESENCE_UPDATE(3),
    VOICE_STATE_UPDATE(4),
    RESUME(6),
    REQUEST_GUILD_MEMBERS(8),
    HELLO(10);

    private final int code;
}
