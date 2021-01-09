package com.luizaprestes.frame.event;

import com.luizaprestes.frame.event.channel.create.TextChannelCreateEvent;
import com.luizaprestes.frame.event.guild.message.GuildMessageReceivedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventType {

    /*
    TEXT CHANNEL EVENTS
     */
    TEXT_CHANNEL_CREATE(TextChannelCreateEvent.class),

    /*
    GUILD EVENTS
     */
    GUILD_MESSAGE_RECEIVED(GuildMessageReceivedEvent.class);

    private final Class<?> eventClass;

}
