package com.luizaprestes.frame.event;

import com.luizaprestes.frame.Frame;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Event {

    private final Frame client;
    private final int responseNumber;

}
