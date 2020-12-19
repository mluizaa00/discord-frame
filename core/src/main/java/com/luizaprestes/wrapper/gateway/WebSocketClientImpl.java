package com.luizaprestes.wrapper.gateway;

import com.luizaprestes.wrapper.WrapperClient;
import lombok.Getter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;

@Getter
public class WebSocketClientImpl extends WebSocketClient {

    protected boolean connected;
    protected WrapperClient api;
    protected long keepAliveInterval;

    public WebSocketClientImpl(String url, WrapperClient api) {
        super(URI.create(url.replace("wss", "ws")));
        this.api = api;
        this.connect();
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        if (api.getAuthToken().isEmpty()) {
            System.out.println("Token not registered.");
        }

        final JSONObject connectObj = new JSONObject()
          .put("op", 2)
          .put("d", new JSONObject()
            .put("token", api.getAuthToken())
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
    public void onMessage(String message) {
        System.out.println(message);

        JSONObject content = new JSONObject(message);

        final String type = content.getString("t");
        content = content.getJSONObject("d");

        if (type.equals("READY")) {
            keepAliveInterval = content.getLong("heartbeat_interval");

            System.out.println("DISCORD WRAPPER IS READY!");

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

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("The connection was closed!");
        System.out.println("By remote? " + remote);
        System.out.println("Reason: " + reason);
        System.out.println("Close code: " + code);
    }

    @Override
    public void onError(Exception exception) {
        exception.printStackTrace();
    }
}
