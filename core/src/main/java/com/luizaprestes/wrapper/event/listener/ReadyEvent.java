package com.luizaprestes.wrapper.event.listener;

import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.event.Event;

public class ReadyEvent extends Event {

    public ReadyEvent(WrapperClient client, int responseNumber) {
        super(client, responseNumber);
    }

}
