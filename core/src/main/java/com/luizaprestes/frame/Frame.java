package com.luizaprestes.frame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.flogger.FluentLogger;
import com.luizaprestes.frame.entities.guild.registry.GuildRegistry;
import com.luizaprestes.frame.entities.user.SelfUser;
import com.luizaprestes.frame.entities.user.registry.UserRegistry;
import com.luizaprestes.frame.entities.user.model.OnlineStatus;
import com.luizaprestes.frame.event.client.EventClient;
import com.luizaprestes.frame.event.client.EventLoader;
import com.luizaprestes.frame.gateway.Status;
import com.luizaprestes.frame.handler.EntityBuilder;
import com.luizaprestes.frame.utils.Constants;
import com.luizaprestes.frame.gateway.WebSocketClientImpl;
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

    private final OkHttpClient httpClient;
    private final WebSocketClientImpl webSocketClient;

    private final EventClient eventClient;
    protected final EventLoader eventLoader;

    protected final EntityBuilder entityBuilder;

    private final UserRegistry userRegistry;
    private final GuildRegistry guildRegistry;

    private final String token;
    private final OnlineStatus onlineStatus;

    @Setter
    private SelfUser selfUser;

    protected final ObjectMapper mapper = new ObjectMapper();
    protected final FluentLogger logger = FluentLogger.forEnclosingClass();

    public Frame(@NotNull String token) {
        this.token = token;

        this.status = Status.LOADING;
        this.httpClient = new OkHttpClient();
        this.webSocketClient = new WebSocketClientImpl(Constants.GATEWAY, this);

        this.eventClient = new EventClient();
        this.eventLoader = new EventLoader(this);
        this.entityBuilder = new EntityBuilder(this);

        this.userRegistry = new UserRegistry();
        this.guildRegistry = new GuildRegistry();

        this.onlineStatus = getOnlineStatus() != null ? getOnlineStatus() : OnlineStatus.ONLINE;
    }

}
