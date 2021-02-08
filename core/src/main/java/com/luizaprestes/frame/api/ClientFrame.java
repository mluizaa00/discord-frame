package com.luizaprestes.frame.api;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.enums.OnlineStatus;
import com.luizaprestes.frame.utils.Configuration;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class ClientFrame implements FrameLoader {

    private Frame frame;
    private Configuration config;

    @Setter
    public OnlineStatus status;

    public void setToken(@NotNull String token) {
        this.frame = new Frame(token);
        this.config = frame.getConfiguration();
    }

    public void build() {
        if (!frame.connect()) frame.getLogger().atWarning().log("The Discord-Frame is already connected!");
    }

    public void shutdown() {
        if (!frame.shutdown()) frame.getLogger().atWarning().log("The Discord-Frame is already disconnected!");
    }


    public void registerEvents(Object... holders) {
        frame.getEventLoader().loadEvents(holders);
    }

    public void removeEvents(Object... holders) {
        frame.getEventLoader().removeEvents(holders);
    }

}
