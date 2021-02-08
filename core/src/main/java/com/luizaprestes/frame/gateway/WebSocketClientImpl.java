package com.luizaprestes.frame.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.gateway.payload.OpCode;
import com.luizaprestes.frame.handlers.impl.ReadyHandler;
import com.luizaprestes.frame.handlers.impl.channel.ChannelCreateHandler;
import com.luizaprestes.frame.utils.Constants;
import com.luizaprestes.frame.utils.JacksonAdapter;
import lombok.Getter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

import static com.luizaprestes.frame.gateway.payload.EventPayloads.*;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Getter
public class WebSocketClientImpl extends WebSocketClient {

    public boolean connected;
    private long keepAliveInterval;

    private final Frame client;

    private final JacksonAdapter adapter;

    public WebSocketClientImpl(String url, @NotNull Frame client) {
        super(URI.create(url));

        this.client = client;
        this.adapter = client.getJacksonAdapter();
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        final ObjectNode propertiesPayload = adapter.createNode()
          .put("$os", System.getProperty("os.name"))
          .put("$browser", Constants.PROJECT_NAME)
          .put("$device", Constants.PROJECT_NAME);

        final ObjectNode presencePayload = adapter.createNode()
          .put("status", client.getOnlineStatus().getKey())
          .put("afk", false);

        final ObjectNode infoPayload = adapter.createNode()
          .put("token", client.getToken())
          .put("intents", 513)
          .set("properties", propertiesPayload);
        infoPayload
          .put("v", 8)
          .set("presence", presencePayload);

        final ObjectNode connectPayload = adapter.createNode()
          .put("op", OpCode.IDENTIFY.getCode())
          .set("d", infoPayload);

        try {
            send(adapter.toJson(connectPayload));
            this.connected = true;
            client.setConnected(true);

            sendUpdates();
        } catch (JsonProcessingException exception) {
            client.getLogger().atSevere().log("Unable to send identify payload, value: %s", exception.getMessage());
        }
    }

    @Override
    public void onMessage(String context) {
        try {
            final ObjectNode content = adapter.getNode(context);
            final ObjectNode insideContent = adapter.getNode(adapter.getString(content, "d"));

            final int opCode = adapter.getInt(content, "op");
            if (opCode == OpCode.HELLO.getCode()) {
                this.keepAliveInterval = adapter.getLong(insideContent, "heartbeat_interval");
            }

            String type = null;
            if (opCode == OpCode.DISPATCH.getCode()) {
                type = adapter.getString(content, "t");
            }

            if (opCode == OpCode.RECONNECT.getCode()) {
                this.reconnect();
            }

            final int responseNumber = adapter.getInt(content, "s");
            final String value = adapter.toJson(insideContent);
            if (type != null) switch (type) {
                case READY: {
                    new ReadyHandler(client, responseNumber, client.getEntityBuilder()).handle(value);

                    keepAlive();
                    client.setStatus(Status.READY);
                    client.getLogger().atInfo().log("Discord Java Wrapper is ready.");
                    break;
                }
                case CHANNEL_CREATE: {
                    new ChannelCreateHandler(client, responseNumber).handle(value);
                    break;
                }
                case GUILD_CREATE: {
                    client.getEntityBuilder().createGuild(value);
                    break;
                }
                default: {
                    client.getLogger().atSevere().log("Payload event was not recognized. Value: %s", type);
                }
            }
        } catch (Exception exception) {
            client.getLogger().atSevere().log(
                "A error occurred while reading json from websocket message. Value: %s",
                exception.getMessage()
            );
        }

    }

    public void sendUpdates() {
        final Thread updates = new Thread("Updates") {

            @Override
            public void run() {
                try {

                    while (this.isAlive() && !this.isInterrupted()) {
                        client.getLogger().atInfo().log("Connected: %s", connected);
                        client.getLogger().atInfo().log("Heartbeat interval: %s", keepAliveInterval);
                        Thread.sleep(60000);
                    }

                } catch (InterruptedException exception) {
                    client.getLogger().atSevere().log(
                      "The thread Updates has been interrupted. Value: %s",
                      exception.getMessage()
                    );

                    System.exit(0);
                    client.setStatus(Status.OFFLINE);
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
                        final ObjectNode heartbeatPayload = adapter.createNode()
                          .put("op", OpCode.HEARTBEAT.getCode())
                          .put("d", System.currentTimeMillis());

                        send(adapter.toJson(heartbeatPayload));
                        Thread.sleep(60000);
                    }

                } catch (Exception exception) {
                    client.getLogger().atSevere().log(
                      "The thread Heartbeat has been interrupted. Value: %s",
                      exception.getMessage()
                    );

                    System.exit(0);
                    client.setStatus(Status.OFFLINE);
                }
            }
        };

        heartbeatThread.setPriority((Thread.NORM_PRIORITY + Thread.MAX_PRIORITY) / 2);
        heartbeatThread.setDaemon(true);
        heartbeatThread.start();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        client.getLogger().atSevere().log(
          "The Discord-Frame connection was closed! By remote? %s, Reason: %s, Close code: %s",
          remote, reason, code);

        this.connected = false;
        client.setStatus(Status.OFFLINE);
    }

    @Override
    public void onError(Exception exception) {
        client.getLogger().atSevere().log("A error occurred on the WebSocket, value: %s", exception.getMessage());
    }

}
