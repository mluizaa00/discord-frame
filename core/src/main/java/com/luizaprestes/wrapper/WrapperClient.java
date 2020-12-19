package com.luizaprestes.wrapper;

import com.luizaprestes.info.Constants;
import com.luizaprestes.wrapper.gateway.WebSocketClientImpl;
import com.luizaprestes.wrapper.gateway.requests.RequestType;
import lombok.Getter;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.UnsupportedEncodingException;

@Getter
public class WrapperClient {

    protected String email;
    protected String password;

    private String authToken;
    private String gateway;

    private WebSocketClientImpl webSocketClient;

    private final JSONParser parser;

    public WrapperClient(String email, String password) {
        this.parser = new JSONParser();

        this.email = email;
        this.password = password;
    }

    public void login() {
        try {
            this.authToken = getToken();
            this.webSocketClient = new WebSocketClientImpl(obtainGateway(), this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private String obtainGateway() {
        try {
            this.gateway = getGateway();
        } catch (Exception exception) {
            System.out.println("Error while obtaining gateway.");
            exception.printStackTrace();
        }
        System.out.println("Obtained gateway: " + gateway);
        return gateway;
    }

    private String getToken() throws UnsupportedEncodingException, ParseException {
        return (String) ((JSONObject) parser.parse(
          RequestType.POST.makeRequest(Constants.LOGIN,
          new StringEntity("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"),
          new BasicNameValuePair("content-type", "application/json")))).get("token"
        );
    }

    private String getGateway() throws ParseException {
        return ((String) ((JSONObject) parser.parse(
          RequestType.GET.makeRequest(Constants.GATEWAY,
          new BasicNameValuePair("authorization", getAuthToken())))).get("url")).replaceAll("wss", "ws"
        );
    }

}
