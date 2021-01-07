package com.luizaprestes.wrapper.event;

import com.luizaprestes.wrapper.event.listener.channel.TextChannelCreateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventType {

    TEXT_CHANNEL_CREATE(TextChannelCreateEvent.class);

    private final Class eventClass;
}
