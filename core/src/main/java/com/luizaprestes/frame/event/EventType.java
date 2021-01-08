package com.luizaprestes.frame.event;

import com.luizaprestes.frame.event.impl.channel.create.TextChannelCreateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventType {

    TEXT_CHANNEL_CREATE(TextChannelCreateEvent.class);

    private final Class<?> eventClass;
}
