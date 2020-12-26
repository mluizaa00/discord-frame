package com.luizaprestes.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizaprestes.wrapper.entity.guild.GuildRegistry;
import com.luizaprestes.wrapper.entity.user.SelfInfo;
import com.luizaprestes.wrapper.entity.user.UserRegistry;
import com.luizaprestes.wrapper.entity.user.model.OnlineStatus;
import com.luizaprestes.wrapper.event.client.EventClient;
import com.luizaprestes.wrapper.handler.client.EntityBuilder;
import com.luizaprestes.wrapper.handler.client.HandlerClient;
import com.luizaprestes.wrapper.util.Constants;
import com.luizaprestes.wrapper.gateway.WebSocketClientImpl;
import com.luizaprestes.wrapper.gateway.request.RequestType;
import lombok.Getter;
import lombok.Setter;


/**
    @author luiza
    @version-implemented 0.0.1
    @since 12.18.2020
 */
@Getter
public class WrapperClient {

    protected final String gateway = Constants.GATEWAY;

    protected RequestType requestType;
    protected WebSocketClientImpl webSocketClient;

    @Setter
    protected SelfInfo selfInfo;

    private final String authToken;

    private final GuildRegistry guildRegistry;
    private final UserRegistry userRegistry;

    private final EntityBuilder entityBuilder;

    private final EventClient eventClient;
    private final HandlerClient handlerClient;

    private final ObjectMapper mapper;

    @Setter
    private OnlineStatus status;

    /**
     * Client loader
     */
    public WrapperClient(String token) {
        this.mapper = new ObjectMapper();
        this.entityBuilder = new EntityBuilder(this);

        this.guildRegistry = new GuildRegistry();
        this.userRegistry = new UserRegistry();

        this.authToken = token;

        this.eventClient = new EventClient();
        this.handlerClient = new HandlerClient(this);

        this.status = OnlineStatus.ONLINE;

    }

    public void login() {
        this.webSocketClient = new WebSocketClientImpl(gateway, this);
    }

}
