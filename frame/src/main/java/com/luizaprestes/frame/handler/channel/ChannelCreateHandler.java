package com.luizaprestes.frame.handler.channel;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.util.JacksonAdapter;
import com.luizaprestes.frame.view.event.client.EventClient;
import com.luizaprestes.frame.view.event.channel.create.TextChannelCreateEvent;
import com.luizaprestes.frame.view.event.channel.create.VoiceChannelCreateEvent;
import com.luizaprestes.frame.handler.SocketHandler;
import com.luizaprestes.frame.factory.EntityFactory;

public final class ChannelCreateHandler extends SocketHandler {

    private final JacksonAdapter adapter;

    private final EventClient eventClient;
    private final EntityFactory builder;

    public ChannelCreateHandler(Frame client, int responseNumber) {
        super(client, responseNumber);

        this.adapter = client.getJacksonAdapter();
        this.eventClient = client.getEventClient();
        this.builder = client.getEntityFactory();
    }

    @Override
    public void handle(String context) {
        try {
            final ObjectNode content = adapter.getNode(context);

            final String type = adapter.getString(content, "type");
            final String id = adapter.getString(content, "guild_id");
            switch (type) {
                case "text": {
                    eventClient.handle(new TextChannelCreateEvent(
                        client, responseNumber,
                        builder.createTextChannel(content.toPrettyString(), id))
                    );
                    break;
                }
                case "voice": {
                    eventClient.handle(new VoiceChannelCreateEvent(
                        client, responseNumber,
                        builder.createVoiceChannel(content.toPrettyString(), id))
                    );
                    break;
                }
                default: {
                    client.getLogger().atSevere().log(
                      "Type from Channel Create payload was not recognized. Type:  %s", type);
                }
            }

        } catch (NullPointerException exception) {
            client.getLogger().atSevere().log(
              "A error occurred while reading payload from Channel Create WebSocket message. Value:  %s",
              exception.getMessage()
            );
        }

    }
}
