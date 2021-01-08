package com.luizaprestes.frame.event.client;

import com.luizaprestes.frame.Frame;

public class EventLoader {

    private final Frame client;

    public EventLoader(Frame client) {
        this.client = client;
    }

    public void loadEvents(Object... holders) {
        for (Object holder : holders) {
            client.getEventClient().register(holder);
        }
    }

    public void removeEvents(Object... holders) {
        for (Object holder : holders) {
            client.getEventClient().remove(holder);
        }
    }

}
