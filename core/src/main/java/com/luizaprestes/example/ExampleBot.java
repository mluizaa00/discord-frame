package com.luizaprestes.example;

import com.luizaprestes.wrapper.WrapperClient;

import static com.luizaprestes.wrapper.util.Config.getConfig;

public class ExampleBot {

    public static void main(String[] args) {
        WrapperClient bot = new WrapperClient(
          getConfig().getString("email"),
          getConfig().getString("password")
        );

        bot.login();

        System.out.println(bot.getAuthToken());
        System.out.println(bot.getEmail());
        System.out.println(bot.getPassword());
        System.out.println(bot.getWebSocketClient().isConnected());
    }
}
