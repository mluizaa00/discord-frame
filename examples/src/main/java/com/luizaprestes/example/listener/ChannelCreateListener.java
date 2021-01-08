package com.luizaprestes.example.listener;

import com.luizaprestes.frame.event.EventAdapter;
import com.luizaprestes.frame.event.EventType;
import com.luizaprestes.frame.event.impl.channel.create.TextChannelCreateEvent;

public class ChannelCreateListener {

    @EventAdapter(
      event = EventType.TEXT_CHANNEL_CREATE
    )
    public void onTextChannelCreateEvent(TextChannelCreateEvent event) {

    }

}
