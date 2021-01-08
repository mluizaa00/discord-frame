package com.luizaprestes.frame.event;

import com.luizaprestes.frame.Frame;
import lombok.Getter;

@Getter
public abstract class Event {

    private final Frame client;
    private final int responseNumber;

    public Event(Frame client, int responseNumber) {
        this.client = client;
        this.responseNumber = responseNumber;
    }

}
