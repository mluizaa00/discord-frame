package com.luizaprestes.wrapper.gateway.request;

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
    RECONNECT(7),
    REQUEST_GUILD_MEMBERS(8),
    INVALID_SESSION(9),
    HELLO(10);

    private final int code;

}
