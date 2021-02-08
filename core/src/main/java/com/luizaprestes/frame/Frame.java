package com.luizaprestes.frame;

import com.google.common.flogger.FluentLogger;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.user.SelfUser;
import com.luizaprestes.frame.entities.user.User;
import com.luizaprestes.frame.enums.OnlineStatus;
import com.luizaprestes.frame.event.client.EventClient;
import com.luizaprestes.frame.event.client.EventLoader;
import com.luizaprestes.frame.gateway.Status;
import com.luizaprestes.frame.handlers.EntityBuilder;
import com.luizaprestes.frame.registries.WeakRegistry;
import com.luizaprestes.frame.utils.Configuration;
import com.luizaprestes.frame.utils.Constants;
import com.luizaprestes.frame.gateway.WebSocketClientImpl;
import com.luizaprestes.frame.utils.JacksonAdapter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

/**
    @author luiza
    @version-implemented 0.0.1
    @since 12.18.2020

    The Frame class is the base of creating the wrapper, it contains all infos necessary to iniciate
    the client.

 */
@Getter
public class Frame {

    @Setter
    private Status status;

    @Getter(AccessLevel.PACKAGE)
    private final WebSocketClientImpl webSocketClient;

    protected final JacksonAdapter jacksonAdapter = new JacksonAdapter();

    private final OkHttpClient httpClient = new OkHttpClient();

    private final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final EventClient eventClient;
    protected final EventLoader eventLoader;

    protected final EntityBuilder entityBuilder;

    private final WeakRegistry<String, User> userRegistry;
    private final WeakRegistry<String, Guild> guildRegistry;

    private final String token;
    private final OnlineStatus onlineStatus;

    private final Configuration configuration;

    @Setter
    private boolean connected;

    @Setter
    private SelfUser selfUser;

    public Frame(@NotNull String token) {
        this.token = token;

        this.status = Status.LOADING;
        this.webSocketClient = new WebSocketClientImpl(Constants.GATEWAY, this);

        this.eventClient = new EventClient();
        this.eventLoader = new EventLoader(this);
        this.entityBuilder = new EntityBuilder(this);

        this.userRegistry = new WeakRegistry<>();
        this.guildRegistry = new WeakRegistry<>();

        this.configuration = new Configuration(this, "config");

        this.onlineStatus = getOnlineStatus() != null ? getOnlineStatus() : OnlineStatus.ONLINE;
    }

    public boolean connect() {
        if (!connected) {
            webSocketClient.connect();
            return true;
        }

        return false;
    }

    public boolean shutdown() {
        if (connected) {
            webSocketClient.close();
            return true;
        }

        return false;
    }

}
