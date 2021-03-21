package com.luizaprestes.frame.view.event.client;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.view.event.EventAdapter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventLoader {

    private final Frame client;

    public void loadEvents(Object... holders) {
        for (Object holder : holders) {
            if (holder.getClass().getAnnotationsByType(EventAdapter.class).length >= 1)  {
                client.getEventClient().register(holder);
            }
        }
    }

    public void removeEvents(Object... holders) {
        for (Object holder : holders) {
            client.getEventClient().remove(holder);
        }
    }

}
