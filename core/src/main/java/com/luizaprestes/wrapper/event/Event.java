package com.luizaprestes.wrapper.event;

import com.luizaprestes.wrapper.WrapperClient;
import lombok.Getter;

@Getter
public abstract class Event {

    protected final WrapperClient client;
    protected final int responseNumber;

    public Event(WrapperClient client, int responseNumber) {
        this.client = client;
        this.responseNumber = responseNumber;
    }

}
