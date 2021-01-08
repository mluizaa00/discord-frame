package com.luizaprestes.frame.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.gateway.payload.OpCode;
import com.luizaprestes.frame.handler.impl.ReadyHandler;
import com.luizaprestes.frame.handler.impl.channel.ChannelCreateHandler;
import com.luizaprestes.frame.utils.Constants;
import com.luizaprestes.frame.utils.Logger;
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

    private final Logger logger;
    private final ObjectMapper map;

    public WebSocketClientImpl(String url, @NotNull Frame client) {
        super(URI.create(url));

        this.client = client;
        this.map = client.getMapper();
        this.logger = new Logger(WebSocketClientImpl.class, false);

        this.connect();
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        final ObjectNode propertiesPayload = map.createObjectNode()
          .put("$os", System.getProperty("os.name"))
          .put("$browser", Constants.PROJECT_NAME)
          .put("$device", Constants.PROJECT_NAME);

        final ObjectNode presencePayload = map.createObjectNode()
          .put("status", client.getStatus().getKey())
          .put("afk", false);

        final ObjectNode infoPayload = map.createObjectNode()
          .put("token", client.getToken())
          .put("intents", 513)
          .set("properties", propertiesPayload);
        infoPayload
          .put("v", 8)
          .set("presence", presencePayload);

        final ObjectNode connectPayload = map.createObjectNode()
          .put("op", OpCode.IDENTIFY.getCode())
          .set("d", infoPayload);

        try {
            send(map.writeValueAsString(connectPayload));
            this.connected = true;

            sendUpdates();
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
            logger.error("Unable to send identify payload.");
        }
    }

    @Override
    public void onMessage(String context) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);

            final int opCode = content.get("op").intValue();
            if (opCode == OpCode.HELLO.getCode()) {
                this.keepAliveInterval = content.get("d").get("heartbeat_interval").asLong();
            }

            String type = null;
            if (opCode == OpCode.DISPATCH.getCode()) {
                type = content.get("t").textValue();
            }

            if (opCode == OpCode.RECONNECT.getCode()) {
                this.reconnect();
            }

            final int responseNumber = content.get("s").asInt();
            final String value = content.get("d").toPrettyString();
            if (type != null) switch (type) {
                case READY: {
                    new ReadyHandler(client, responseNumber, client.getEntityBuilder())
                      .handle(value);

                    keepAlive();
                    logger.info("Discord Java Wrapper is ready.");
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
                    logger.error("Payload event was not recognized. Value: " + type);
                }
            }
        } catch (Exception exception) {
            logger.error("A error occurred while reading json from websocket message.");
            exception.printStackTrace();
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
                        Thread.sleep(60000);
                    }
                } catch (InterruptedException exception) {
                    exception.printStackTrace();

                    logger.error("The thread Updates has been interrupted");
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
                        final ObjectNode heartbeatPayload = map.createObjectNode()
                          .put("op", OpCode.HEARTBEAT.getCode())
                          .put("d", System.currentTimeMillis());

                        send(map.writeValueAsString(heartbeatPayload));
                        Thread.sleep(60000);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();

                    logger.error("The thread Heartbeat has been interrupted");
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
