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
          getConfig().getString("token")
        );

        client.login();
    }

}
