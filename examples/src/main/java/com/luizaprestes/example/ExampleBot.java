package com.luizaprestes.example;

import com.luizaprestes.example.listener.ChannelCreateListener;
import com.luizaprestes.wrapper.ClientFrame;
import com.luizaprestes.wrapper.entity.user.model.OnlineStatus;

import static com.luizaprestes.wrapper.util.Config.getConfig;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
public class ExampleBot {

    public static void main(String[] args) {
        final ClientFrame client = new ClientFrame(
          getConfig().getString("token")
        );

        client.registerEvents(
          new ChannelCreateListener()
        );

        client.setStatus(OnlineStatus.DO_NOT_DISTURB);
        client.login();
    }

}
