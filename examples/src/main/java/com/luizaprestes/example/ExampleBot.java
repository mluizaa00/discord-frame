package com.luizaprestes.example;

import com.luizaprestes.wrapper.WrapperClient;

import static com.luizaprestes.wrapper.util.Config.*;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
public class ExampleBot {

    public static void main(String[] args) {
        final WrapperClient client = new WrapperClient(
          getConfig().getString("email"),
          getConfig().getString("password")
        );

        client.login();

        /*
        SIMPLE DEBUG
         */
        System.out.println("Token: " + client.getAuthToken());
        System.out.println("Email: " + client.getEmail());
        System.out.println("Password: " + client.getPassword());
        System.out.println("Connected: " + client.getWebSocketClient().connected);

        System.out.println("Heartbeat interval: " + client.getWebSocketClient().getKeepAliveInterval());
        System.out.println("URI: " + client.getWebSocketClient().getURI().toString());
    }

}
