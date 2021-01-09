package com.luizaprestes.frame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.flogger.FluentLogger;
import com.luizaprestes.frame.entities.guild.registry.GuildRegistry;
import com.luizaprestes.frame.entities.user.SelfInfo;
import com.luizaprestes.frame.entities.user.registry.UserRegistry;
import com.luizaprestes.frame.entities.user.model.OnlineStatus;
import com.luizaprestes.frame.event.client.EventClient;
import com.luizaprestes.frame.event.client.EventLoader;
import com.luizaprestes.frame.handler.EntityBuilder;
import com.luizaprestes.frame.utils.Constants;
import com.luizaprestes.frame.gateway.WebSocketClientImpl;
import lombok.Getter;
import lombok.Setter;


/**
    @author luiza
    @version-implemented 0.0.1
    @since 12.18.2020
 */
@Getter
public class Frame {

    private final String gateway = Constants.GATEWAY;

    private final FluentLogger logger;

    private WebSocketClientImpl webSocketClient;

    @Setter
    private SelfInfo selfInfo;

    private final String token;

    private final GuildRegistry guildRegistry;
    private final UserRegistry userRegistry;

    private final EntityBuilder entityBuilder;

    private final EventClient eventClient;
    private final EventLoader eventLoader;

    private final ObjectMapper mapper;

    @Setter
    public OnlineStatus status;

    /**
     * Client loader
     */
    public Frame(String token) {
        this.token = token;

        this.mapper = new ObjectMapper();
        this.entityBuilder = new EntityBuilder(this);

        this.guildRegistry = new GuildRegistry();
        this.userRegistry = new UserRegistry();

        this.eventClient = new EventClient();
        this.eventLoader = new EventLoader(this);

        this.logger = FluentLogger.forEnclosingClass();

    }

    public void login() {
        this.webSocketClient = new WebSocketClientImpl(gateway, this);
    }

}
