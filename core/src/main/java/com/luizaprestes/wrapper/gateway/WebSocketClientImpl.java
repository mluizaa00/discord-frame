package com.luizaprestes.wrapper.gateway;

import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.util.Logger;
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

    protected final Logger logger = new Logger(WebSocketClientImpl.class, false);

    public WebSocketClientImpl(String url, WrapperClient client) {
        super(URI.create(url));

        this.client = client;
        this.connect();
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        final JSONObject connectObj = new JSONObject()
          .put("op", OpCodes.IDENTIFY.getCode())
          .put("d", new JSONObject()
            .put("token", client.getAuthToken())
            .put("intents", 513)
            .put("properties", new JSONObject()
              .put("$os", System.getProperty("os.name"))
              .put("$browser", "Java Discord Wrapper")
              .put("$device", "Java Discord Wrapper")
            )
            .put("v", 8)
            .put("presence", new JSONObject()
              .put("status", client.getStatus().getKey())
              .put("afk", false)
            )
          );

        send(connectObj.toString());
        this.connected = true;

        sendUpdates();
    }

    @Override
    public void onMessage(String context) {
        final JSONObject contextObj = new JSONObject(context);
        final JSONObject content = contextObj.isNull("d") ? null : contextObj.getJSONObject("d");

        final int opCode = contextObj.getInt("op");
        if (opCode == OpCodes.HELLO.getCode()) {
            assert content != null;
            this.keepAliveInterval = content.getInt("heartbeat_interval");
            logger.debug(context);
        }

        String type = null;
        if (opCode == OpCodes.DISPATCH.getCode()) {
            type = contextObj.getString("t");
            logger.debug(context);
        }

        if (opCode == OpCodes.RECONNECT.getCode()) {
            this.reconnect();
            logger.debug(context);
        }

        if (type != null) {
            switch (type) {
                case "READY": {
                    assert content != null;
                    client.getHandlerClient().getReadyHandler().handle(content);

                    keepAlive();
                    logger.info("Discord Java Wrapper is ready.");
                    break;
                }
                default: {
                    System.out.println(type);
                }
            }
        }

    }

    public void sendUpdates() {
        final Thread updates = new Thread("Updates") {

            @Override
            public void run() {
                try {
                    while (this.isAlive() && !this.isInterrupted()) {
                        logger.debug("Connected: " + connected);
                        logger.debug("Heartbeat interval: " + keepAliveInterval);
                        logger.debug("URI: " + uri.toString());
                        Thread.sleep(60000);
                    }
                } catch (InterruptedException exception) {
                    exception.printStackTrace();

                    logger.error("The thread: Updates, has been interrupted");
                    System.exit(0);
                }
            }
        };

        updates.start();
    }

    public void keepAlive() {
        final Thread heartbeatThread = new Thread("Heartbeat") {

            @Override
            public void run() {
                try {
                    while (this.isAlive() && !this.isInterrupted()) {
                        send(
                          new JSONObject()
                            .put("op", OpCodes.HEARTBEAT.getCode())
                            .put("d", System.currentTimeMillis()).toString()
                        );
                        Thread.sleep(60000);
                    }
                } catch (InterruptedException exception) {
                    exception.printStackTrace();

                    logger.error("The thread: Heartbeat, has been interrupted");
                    System.exit(0);
                }
            }
        };

        heartbeatThread.setPriority((Thread.NORM_PRIORITY + Thread.MAX_PRIORITY) / 2);
        heartbeatThread.setDaemon(true);
        heartbeatThread.start();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.error("The connection was closed!");
        logger.error("By remote? " + remote);
        logger.error("Reason: " + reason);
        logger.error("Close code: " + code);

        this.connected = false;
    }

    @Override
    public void onError(Exception exception) {
        exception.printStackTrace();
    }

}
