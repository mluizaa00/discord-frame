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
        System.out.println(client.getAuthToken());
        System.out.println(client.getEmail());
        System.out.println(client.getPassword());
        System.out.println(client.getWebSocketClient().connected);
    }

}
