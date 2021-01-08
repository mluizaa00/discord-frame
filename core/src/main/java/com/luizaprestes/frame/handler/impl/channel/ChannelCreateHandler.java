package com.luizaprestes.frame.handler.impl.channel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.event.client.EventClient;
import com.luizaprestes.frame.event.impl.channel.create.TextChannelCreateEvent;
import com.luizaprestes.frame.event.impl.channel.create.VoiceChannelCreateEvent;
import com.luizaprestes.frame.handler.SocketHandler;
import com.luizaprestes.frame.handler.EntityBuilder;
import com.luizaprestes.frame.utils.Logger;

import java.util.Objects;

public class ChannelCreateHandler extends SocketHandler {

    private final ObjectMapper map;
    private final Logger logger = new Logger(ChannelCreateHandler.class, false);

    private final EventClient eventClient;
    private final EntityBuilder builder;

    public ChannelCreateHandler(Frame client, int responseNumber) {
        super(client, responseNumber);

        this.map = client.getMapper();
        this.eventClient = client.getEventClient();
        this.builder = client.getEntityBuilder();
    }

    @Override
    public void handle(String context) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);

            final String type = Objects.requireNonNull(content.get("type").asText());


            final String id = content.get("guild_id").textValue();
            switch (type) {
                case "text": {
                    eventClient.handle(
                      new TextChannelCreateEvent(
                        client, responseNumber,
                        builder.createTextChannel(content.toPrettyString(), id))
                    );
                    break;
                }
                case "voice": {
                    eventClient.handle(
                      new VoiceChannelCreateEvent(
                        client, responseNumber,
                        builder.createVoiceChannel(content.toPrettyString(), id))
                    );
                }
                default: {
                    logger.error("Type from Channel Create payload was not recognized. Type: " + type);
                }
            }

        } catch (JsonProcessingException | NullPointerException exception) {
            logger.error("A error occurred while reading payload from Channel Create WebSocket message.");
            exception.printStackTrace();
        }

    }
}
