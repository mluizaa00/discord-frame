package com.luizaprestes.frame.api;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.user.model.OnlineStatus;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class ClientFrame implements FrameLoader {

    private Frame frame;

    @Setter
    public OnlineStatus status;

    public void setToken(@NotNull String token) {
        this.frame = new Frame(token);
    }

    public void build() {
        if (!frame.getWebSocketClient().isConnected()) {
            frame.getWebSocketClient().connect();
        } else {
            frame.getLogger().atWarning().log("The Discord-Frame is already connected!");
        }
    }

    public void shutdown() {
        if (frame.getWebSocketClient().isConnected()) {
            frame.getWebSocketClient().close();
        } else {
            frame.getLogger().atWarning().log("The Discord-Frame is already disconnected!");
        }
    }

    public void registerEvents(Object... holders) {
        frame.getEventLoader().loadEvents(holders);
    }

    public void removeEvents(Object... holders) {
        frame.getEventLoader().removeEvents(holders);
    }

}
