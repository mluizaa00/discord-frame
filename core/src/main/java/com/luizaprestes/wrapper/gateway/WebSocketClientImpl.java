package com.luizaprestes.wrapper.gateway;

import com.luizaprestes.wrapper.WrapperClient;
import lombok.Getter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Getter
public class WebSocketClientImpl extends WebSocketClient {

    public boolean connected;

    protected WrapperClient client;
    protected long keepAliveInterval;

    public WebSocketClientImpl(String url, WrapperClient api) {
        super(URI.create(url.replace("wss", "ws")));

        this.client = api;
        this.connect();
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        if (
          client.getAuthToken().isEmpty() ||
            client.getAuthToken() == null
        ) {
            System.out.println("Token not registered.");
        }

        final JSONObject connectObj = new JSONObject()
          .put("op", 2)
          .put("d", new JSONObject()
            .put("token", client.getAuthToken())
            .put("intents", 513)
            .put("properties", new JSONObject()
              .put("$os", System.getProperty("os.name"))
              .put("$browser", "Java Discord Wrapper")
              .put("$device", "Java Discord Wrapper")
            )
            .put("v", 8));

        send(connectObj.toString());

        this.connected = true;
    }

    @Override
    public void onMessage(String context) {
        System.out.println(context);

        JSONObject content = new JSONObject(context);

        final String type = content.getString("t");
        content = content.getJSONObject("d");

        switch (type) {
            case "READY": {
                System.out.println("DISCORD WRAPPER IS READY!");

                client.getEventClient().getReadyHandler().handle(content);
                keepAliveInterval = content.getLong("heartbeat_interval");

                new Thread(() -> {
                    while (!getConnection().isClosed()) {
                        send(new JSONObject().put("op", 1).put("d", System.currentTimeMillis()).toString());

                        try {
                            Thread.sleep(keepAliveInterval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.exit(0);
                        }
                    }
                }).start();
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("The connection was closed!");
        System.out.println("By remote? " + remote);
        System.out.println("Reason: " + reason);
        System.out.println("Close code: " + code);

        this.connected = false;
    }

    @Override
    public void onError(Exception exception) {
        exception.printStackTrace();
    }
}
