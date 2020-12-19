package com.luizaprestes.wrapper;

import com.luizaprestes.wrapper.entities.guild.impl.GuildRegistry;
import com.luizaprestes.wrapper.entities.user.SelfInfo;
import com.luizaprestes.wrapper.entities.user.impl.UserRegistry;
import com.luizaprestes.wrapper.handler.event.ReadyHandler;
import com.luizaprestes.wrapper.handler.impl.EntityBuilder;
import com.luizaprestes.wrapper.util.Constants;
import com.luizaprestes.wrapper.gateway.WebSocketClientImpl;
import com.luizaprestes.wrapper.gateway.request.RequestType;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
    @author luiza
    @version-implemented 0.0.1
    @since 12.18.2020
 */
@Getter
public class WrapperClient {

    protected String email;
    protected String password;

    protected String authToken;
    protected String gateway;

    protected WebSocketClientImpl webSocketClient;

    @Setter
    protected SelfInfo selfInfo;

    private final JSONParser parser;

    private final GuildRegistry guildRegistry;
    private final UserRegistry userRegistry;

    private final ReadyHandler readyHandler;

    private final EntityBuilder entityBuilder;

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

        this.readyHandler = new ReadyHandler(this, entityBuilder);

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
        try {
            final JSONObject obj = ((JSONObject) parser.parse(RequestType.POST.makeRequest(
                Constants.LOGIN,
                new StringEntity("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"),
                new BasicNameValuePair("content-type", "application/json")))
            );

            return (String) obj.get("token");
        } catch (Exception exception) {
            System.out.println("Error while trying to get token.");
            exception.printStackTrace();
        }
        return null;
    }

    private String getGateway() {
        try {
            final JSONObject obj = ((JSONObject) parser.parse(RequestType.GET.makeRequest(
              Constants.GATEWAY, new BasicNameValuePair("authorization", getAuthToken())))
            );

            return ((String) obj.get("url")).replaceAll("wss", "ws");
        } catch (ParseException exception) {
            System.out.println("Error while trying to get gateway.");
            exception.printStackTrace();
        }
        return null;
    }
}
