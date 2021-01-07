package com.luizaprestes.wrapper.event.listener.channel;

import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.event.Event;

public class TextChannelCreateEvent extends Event {

    public TextChannelCreateEvent(WrapperClient client, int responseNumber) {
        super(client, responseNumber);
    }

}
