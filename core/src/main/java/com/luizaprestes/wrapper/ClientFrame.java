package com.luizaprestes.wrapper;

import com.luizaprestes.wrapper.entity.user.model.OnlineStatus;

public class ClientFrame {

    private final WrapperClient client;

    public OnlineStatus status;

    public ClientFrame(String token) {
        this.client = new WrapperClient(token);
    }

    public void login() {
        client.login();
    }

    public void registerEvents(Object... holders) {
        client.getEventLoader().loadEvents(holders);
    }

    public void removeEvents(Object... holders) {
        client.getEventLoader().removeEvents(holders);
    }

    public void setStatus(OnlineStatus status) {
        this.status = status;
        client.setStatus(status);
    }

}
