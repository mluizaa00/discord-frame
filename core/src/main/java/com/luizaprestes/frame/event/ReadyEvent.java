package com.luizaprestes.frame.event;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.event.Event;

public class ReadyEvent extends Event {

    public ReadyEvent(Frame client, int responseNumber) {
        super(client, responseNumber);
    }

}
