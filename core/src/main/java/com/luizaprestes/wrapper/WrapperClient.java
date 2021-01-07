package com.luizaprestes.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizaprestes.wrapper.entity.guild.registry.GuildRegistry;
import com.luizaprestes.wrapper.entity.user.SelfInfo;
import com.luizaprestes.wrapper.entity.user.registry.UserRegistry;
import com.luizaprestes.wrapper.entity.user.model.OnlineStatus;
import com.luizaprestes.wrapper.event.client.EventClient;
import com.luizaprestes.wrapper.event.client.EventLoader;
import com.luizaprestes.wrapper.handler.client.EntityBuilder;
import com.luizaprestes.wrapper.util.Constants;
import com.luizaprestes.wrapper.gateway.WebSocketClientImpl;
import lombok.Getter;
import lombok.Setter;


/**
    @author luiza
    @version-implemented 0.0.1
    @since 12.18.2020
 */
@Getter
public class WrapperClient {

    private final String gateway = Constants.GATEWAY;

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
    public WrapperClient(String token) {
        this.token = token;

        this.mapper = new ObjectMapper();
        this.entityBuilder = new EntityBuilder(this);

        this.guildRegistry = new GuildRegistry();
        this.userRegistry = new UserRegistry();

        this.eventClient = new EventClient();
        this.eventLoader = new EventLoader(this);

    }

    public void login() {
        this.webSocketClient = new WebSocketClientImpl(gateway, this);
    }

}
