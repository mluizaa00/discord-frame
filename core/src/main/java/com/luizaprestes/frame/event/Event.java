package com.luizaprestes.frame.event;

import com.luizaprestes.frame.Frame;
import lombok.Getter;

@Getter
public abstract class Event {

    protected final Frame client;
    protected final int responseNumber;

    public Event(Frame client, int responseNumber) {
        this.client = client;
        this.responseNumber = responseNumber;
    }

}
