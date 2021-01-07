package com.luizaprestes.example.listener;

import com.luizaprestes.wrapper.event.EventAdapter;
import com.luizaprestes.wrapper.event.EventType;
import com.luizaprestes.wrapper.event.listener.channel.TextChannelCreateEvent;

public class ChannelCreateListener {

    @EventAdapter(
      event = EventType.TEXT_CHANNEL_CREATE
    )
    public void onTextChannelCreateEvent(TextChannelCreateEvent event) {

    }

}
