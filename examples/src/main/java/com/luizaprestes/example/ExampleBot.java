package com.luizaprestes.example;

import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.entity.user.model.OnlineStatus;

import static com.luizaprestes.wrapper.util.Config.*;

/**
 @author luiza
 @version-implemented 0.0.1
 @date 12.19.2020
 */
public class ExampleBot {

    public static void main(String[] args) {
        final WrapperClient client = new WrapperClient(
          getConfig().getString("token")
        );

        client.setStatus(OnlineStatus.DO_NOT_DISTURB);
        client.login();
    }

}
