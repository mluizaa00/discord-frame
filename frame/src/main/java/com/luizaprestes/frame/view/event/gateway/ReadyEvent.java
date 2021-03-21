package com.luizaprestes.frame.view.event.gateway;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.view.event.Event;

public class ReadyEvent extends Event {

    public ReadyEvent(Frame client, int responseNumber) {
        super(client, responseNumber);
    }

}
