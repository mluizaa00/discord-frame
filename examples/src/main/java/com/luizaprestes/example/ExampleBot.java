package com.luizaprestes.example;

import com.luizaprestes.example.listener.ChannelCreateListener;
import com.luizaprestes.frame.api.ClientFrame;
import com.luizaprestes.frame.enums.OnlineStatus;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020

 This is a example of a application using discord-frame.

 */
public class ExampleBot extends ClientFrame {

    @Override
    public void onLoad() {
        setToken(getConfig().getString("token"));

        registerEvents(
          new ChannelCreateListener()
        );

        setStatus(OnlineStatus.DO_NOT_DISTURB);
        build();
    }

    public static void main(String[] args) {
        final ExampleBot exampleBot = new ExampleBot();

        exampleBot.onLoad();

    }

}
