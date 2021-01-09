package com.luizaprestes.frame.handler.impl.channel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.event.client.EventClient;
import com.luizaprestes.frame.event.channel.create.TextChannelCreateEvent;
import com.luizaprestes.frame.event.channel.create.VoiceChannelCreateEvent;
import com.luizaprestes.frame.handler.SocketHandler;
import com.luizaprestes.frame.handler.EntityBuilder;

import java.util.Objects;

public class ChannelCreateHandler extends SocketHandler {

    private final ObjectMapper map;

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
                    client.getLogger().atSevere().log(
                      "Type from Channel Create payload was not recognized. Type:  %s", type);
                }
            }

        } catch (JsonProcessingException | NullPointerException exception) {
            client.getLogger().atSevere().log(
              "A error occurred while reading payload from Channel Create WebSocket message. Value:  %s", exception.getMessage());
        }

    }
}
