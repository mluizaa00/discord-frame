package com.luizaprestes.frame.event;

import com.luizaprestes.frame.Frame;

public class ReadyEvent extends Event {

    public ReadyEvent(Frame client, int responseNumber) {
        super(client, responseNumber);
    }

}
