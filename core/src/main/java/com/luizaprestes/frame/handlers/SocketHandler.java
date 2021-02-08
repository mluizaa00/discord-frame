package com.luizaprestes.frame.handlers;

import com.luizaprestes.frame.Frame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class SocketHandler {

    protected Frame client;
    protected int responseNumber;

    public abstract void handle(String context);

}
