package com.luizaprestes.wrapper;

import com.luizaprestes.wrapper.entities.guild.GuildRegistry;
import com.luizaprestes.wrapper.entities.user.SelfInfo;
import com.luizaprestes.wrapper.entities.user.UserRegistry;
import com.luizaprestes.wrapper.event.client.EventClient;
import com.luizaprestes.wrapper.handler.impl.EntityBuilder;
import com.luizaprestes.wrapper.util.Constants;
import com.luizaprestes.wrapper.gateway.WebSocketClientImpl;
import com.luizaprestes.wrapper.gateway.request.RequestType;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.parser.JSONParser;

/**
    @author luiza
    @version-implemented 0.0.1
    @since 12.18.2020
 */
@Getter
public class WrapperClient {

    protected final String email;
    protected String password;

    protected String authToken;
    protected String gateway;

    protected WebSocketClientImpl webSocketClient;

    @Setter
    protected SelfInfo selfInfo;

    private final JSONParser parser;

    private final GuildRegistry guildRegistry;
    private final UserRegistry userRegistry;

    private final EntityBuilder entityBuilder;

    private final EventClient eventClient;

    /**
     * Client loader
     */
    public WrapperClient(String email, String password) {
        this.parser = new JSONParser();
        this.entityBuilder = new EntityBuilder(this);

        this.email = email;
        this.password = password;

        this.guildRegistry = new GuildRegistry();
        this.userRegistry = new UserRegistry();

        this.eventClient = new EventClient(this);

    }

    public void login() {
        this.authToken = getToken();
        this.webSocketClient = new WebSocketClientImpl(obtainGateway(), this);
    }

    private String obtainGateway() {
        this.gateway = getGateway();

        System.out.println("Obtained gateway: " + gateway);
        return gateway;
    }

    private String getToken() {
        return RequestType.makePostRequest(
          Constants.LOGIN,
          "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}",
          "token"
        );
    }

    private String getGateway() {
        return RequestType.makeGetRequest(
          Constants.GATEWAY, "url", authToken
        );
    }
}
