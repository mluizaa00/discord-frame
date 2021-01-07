package com.luizaprestes.wrapper.event.client;

import com.luizaprestes.wrapper.event.Event;

import java.util.LinkedList;
import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.18.2020
 */
public class EventClient {

    private final EventParser parser;

    public EventClient() {
        this.parser = new EventParser(this);
    }

    public final List<Object> listenerList = new LinkedList<>();

    public void register(Object holder) {
        listenerList.add(holder);
    }

    public void remove(Object holder) {
        listenerList.remove(holder);
    }

    public void handle(Event event) {
        parser.parse(event);
    }

}
