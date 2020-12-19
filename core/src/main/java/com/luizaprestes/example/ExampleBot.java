package com.luizaprestes.example;

import com.luizaprestes.wrapper.WrapperClient;

import static com.luizaprestes.wrapper.util.Config.getConfig;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
public class ExampleBot {

    public static void main(String[] args) {
        WrapperClient bot = new WrapperClient(
          getConfig().getString("email"),
          getConfig().getString("password")
        );

        bot.login();

        /*
        SIMPLE DEBUG
         */
        System.out.println(bot.getAuthToken());
        System.out.println(bot.getEmail());
        System.out.println(bot.getPassword());
        System.out.println(bot.getWebSocketClient().isConnected());
    }
}
