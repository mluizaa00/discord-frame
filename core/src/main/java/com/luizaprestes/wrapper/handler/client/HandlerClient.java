package com.luizaprestes.wrapper.handler.client;

import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.handler.impl.ReadyHandler;
import lombok.Getter;

public class HandlerClient {

    @Getter
    private final ReadyHandler readyHandler;

    public HandlerClient(WrapperClient client) {
        this.readyHandler = new ReadyHandler(client, client.getEntityBuilder());
    }

}
