package com.luizaprestes.wrapper.event.client;

import com.luizaprestes.wrapper.event.Event;
import com.luizaprestes.wrapper.event.IEvent;

import java.util.LinkedList;
import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.18.2020
 */
public class EventClient {

    private final List<IEvent> listenerList = new LinkedList<>();

    public void register(IEvent listener) {
        listenerList.add(listener);
    }

    public void remove(IEvent listener) {
        listenerList.remove(listener);
    }

    public void handle(Event event) {
        for (IEvent listener : listenerList) {
            listener.onEvent(event);
        }
    }

}
