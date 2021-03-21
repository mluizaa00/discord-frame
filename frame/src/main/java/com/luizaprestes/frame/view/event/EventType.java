package com.luizaprestes.frame.view.event;

import com.luizaprestes.frame.view.event.channel.create.TextChannelCreateEvent;
import com.luizaprestes.frame.view.event.guild.message.GuildMessageReceivedEvent;
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
