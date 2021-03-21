package com.luizaprestes.frame.gateway;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.gateway.payload.OpCode;
import com.luizaprestes.frame.util.JacksonAdapter;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

/**
 @author luiza
 @since 12.19.2020
 */
@Getter
@Setter
public class PacketHandler extends WebSocketClient {

    public boolean connected;
    private long keepAliveInterval;

    private final Frame frame;
    private final JacksonAdapter adapter;

    public PacketHandler(final String url, @NotNull final Frame frame) {
        super(URI.create(url));

        this.frame = frame;
        this.adapter = frame.getJacksonAdapter();
    }

    @Override
    public void onOpen(final ServerHandshake handshake) {
        frame.getPacketController().handshake();
    }

    @Override @SneakyThrows
    public void onMessage(final String context) {
        frame.getPacketController().handle(context);
    }

    public void sendUpdates() {
        new Thread() {
            @Override
            public void run() {
                try {
                    while (this.isAlive() && !this.isInterrupted()) {
                        frame.getLogger().atInfo().log("Connected: %s", connected);
                        frame.getLogger().atInfo().log("Heartbeat interval: %s", keepAliveInterval);
                        Thread.sleep(60000);
                    }
                } catch (InterruptedException exception) {

                    frame.setGatewayStatus(GatewayStatus.OFFLINE);
                    throw new RuntimeException(
                        "The thread Updates has been interrupted.",
                        exception
                    );
                }
            }
        }.start();
    }

    public void keepAlive() {
        new Thread("Heartbeat") {
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
                    frame.setGatewayStatus(GatewayStatus.OFFLINE);

                    throw new RuntimeException(
                        "The thread Heartbeat has been interrupted.",
                        exception
                    );
                }
            }
        }.start();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        frame.getLogger().atSevere().log(
          "The Discord-Frame connection was closed! By remote? %s, Reason: %s, Close code: %s",
          remote, reason, code);

        this.connected = false;
        frame.setGatewayStatus(GatewayStatus.OFFLINE);
    }

    @Override
    public void onError(Exception exception) {
        frame.getLogger().atSevere().log("A error occurred on the WebSocket, value: %s", exception.getMessage());
    }

}
