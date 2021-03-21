package com.luizaprestes.frame.handler.ready;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.channel.TextChannel;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.util.JacksonAdapter;
import com.luizaprestes.frame.view.event.gateway.ReadyEvent;
import com.luizaprestes.frame.handler.SocketHandler;
import com.luizaprestes.frame.factory.EntityFactory;

import java.util.ArrayList;

/**
 @author luiza
 @since 12.19.2020
 */
public final class ReadyHandler extends SocketHandler {

    private final Frame frame;
    private final EntityFactory builder;

    private final JacksonAdapter adapter;

    public ReadyHandler(Frame frame, int responseNumber, EntityFactory builder) {
        super(frame, responseNumber);

        this.frame = frame;
        this.builder = builder;
        this.adapter = frame.getJacksonAdapter();
    }

    @Override
    public void handle(String context) {
        try {
            final ObjectNode content = adapter.getNode(context);

            frame.setSelfUser(builder.createSelfInfo(adapter.toJson(adapter.getString(content, "user"))));

            final ArrayNode guilds = (ArrayNode) content.get("guilds");
            final ArrayNode muted = (ArrayNode) content.get("muted_channels");

            final ArrayList<TextChannel> mutedChannels = new ArrayList<>();
            for (int i = 0; i < guilds.size(); i++) {
                final Guild guild = builder.createGuild(guilds.get(i).toPrettyString());

                if (guild == null) break;

                final TextChannel channel = guild.getTextChannels().get(muted.get(i).textValue());
                mutedChannels.add(channel);
            }

            frame.getSelfUser().setMutedChannels(mutedChannels);

            final ArrayNode privateChannels = (ArrayNode) content.get("private_channels");
            for (int i = 0; i < privateChannels.size(); i++) {
                builder.createPrivateChannel(privateChannels.get(i).toPrettyString());
            }

            frame.getEventClient().handle(new ReadyEvent(frame, responseNumber));
            frame.getLogger().atInfo().log("ReadyHandler is ready.");
        } catch (Exception exception) {
            frame.getLogger().atSevere().log(
              "A error occurred while executing ReadyHandler from WebSocket message. Value: %s",
              exception.getMessage()
            );
        }

    }

}
