package com.luizaprestes.frame.view.event.client;

import com.luizaprestes.frame.view.event.Event;

import java.util.LinkedList;
import java.util.List;

/**
 @author luiza
 @since 12.18.2020
 */
public class EventClient {

    private final EventParser parser;

    public EventClient() {
        this.parser = new EventParser();
    }

    public static final List<Object> LISTENER_LIST = new LinkedList<>();

    public void register(Object holder) {
        LISTENER_LIST.add(holder);
    }

    public void remove(Object holder) {
        LISTENER_LIST.remove(holder);
    }

    public void handle(Event event) {
        parser.parse(event);
    }

}
