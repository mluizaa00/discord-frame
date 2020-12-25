package com.luizaprestes.wrapper.event;

import com.luizaprestes.wrapper.WrapperClient;
import lombok.Getter;

@Getter
public abstract class Event {

    protected final WrapperClient client;

    public Event(WrapperClient client) {
        this.client = client;
    }
}
