package com.luizaprestes.wrapper.handler;

import com.luizaprestes.wrapper.WrapperClient;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class SocketHandler {

    protected WrapperClient client;
    protected int responseNumber;

    public abstract void handle(String context);

}
