package com.luizaprestes.frame.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.exception.InvalidRequestException;
import com.luizaprestes.frame.gateway.PacketHandler;
import com.luizaprestes.frame.gateway.GatewayStatus;
import com.luizaprestes.frame.gateway.payload.OpCode;
import com.luizaprestes.frame.handler.ready.ReadyHandler;
import com.luizaprestes.frame.util.Constants;
import com.luizaprestes.frame.util.JacksonAdapter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import static com.luizaprestes.frame.gateway.payload.PacketPayload.GUILD_CREATE;
import static com.luizaprestes.frame.gateway.payload.PacketPayload.READY;

@AllArgsConstructor
public final class PacketController {

    private final Frame frame;

    public void handshake() {
        final JacksonAdapter adapter = frame.getJacksonAdapter();

        final ObjectNode propertiesPayload = adapter.createNode()
                .put("$os", System.getProperty("os.name"))
                .put("$browser", Constants.PROJECT_NAME)
                .put("$device", Constants.PROJECT_NAME);

        final ObjectNode presencePayload = adapter.createNode()
                .put("status", frame.getOnlineStatus().getKey())
                .put("afk", false);

        final ObjectNode infoPayload = adapter.createNode()
                .put("token", frame.getToken())
                .put("intents", 513)
                .set("properties", propertiesPayload);
        infoPayload
                .put("v", 8)
                .set("presence", presencePayload);

        final ObjectNode connectPayload = adapter.createNode()
                .put("op", OpCode.IDENTIFY.getCode())
                .set("d", infoPayload);

        try {
            final PacketHandler handler = frame.getPacketHandler();
            handler.send(adapter.toJson(connectPayload));
            handler.setConnected(true);
            frame.setConnected(true);

            handler.sendUpdates();
        } catch (JsonProcessingException exception) {
            frame.getLogger().atSevere().log("Unable to send identify payload, value: %s", exception.getMessage());
        }
    }

    @SneakyThrows
    public void handle(final String context) {
        try {
            final JacksonAdapter adapter = frame.getJacksonAdapter();
            final PacketHandler handler = frame.getPacketHandler();

            final ObjectNode content = adapter.getNode(context);
            final ObjectNode insideContent = adapter.getNode(adapter.getString(content, "d"));

            final int opCode = adapter.getInt(content, "op");
            if (opCode == OpCode.HELLO.getCode()) {
                handler.setKeepAliveInterval(adapter.getLong(insideContent, "heartbeat_interval"));
            }

            String type = null;
            if (opCode == OpCode.DISPATCH.getCode()) {
                type = adapter.getString(content, "t");
            }

            if (opCode == OpCode.RECONNECT.getCode()) {
                handler.reconnect();
            }

            final int responseNumber = adapter.getInt(content, "s");
            final String value = adapter.toJson(insideContent);
            if (type != null) switch (type) {
                case READY: {
                    new ReadyHandler(frame, responseNumber, frame.getEntityFactory()).handle(value);

                    handler.keepAlive();
                    frame.setGatewayStatus(GatewayStatus.READY);
                    frame.getLogger().atInfo().log("Discord Java Wrapper is ready!");
                    break;
                }
                case GUILD_CREATE: {
                    frame.getEntityFactory().createGuild(value);
                    break;
                }
                default: {
                    frame.getLogger().atSevere().log("Payload event was not recognized. Value: %s", type);
                }
            }
        } catch (Exception exception) {
            throw new InvalidRequestException(
                "A error occurred while reading json from websocket message",
                exception
            );
        }
    }

}
