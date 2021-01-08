package com.luizaprestes.frame;

import com.luizaprestes.frame.entities.user.model.OnlineStatus;

public class ClientFrame {

    private final Frame client;

    public OnlineStatus status;

    public ClientFrame(String token) {
        this.client = new Frame(token);
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
