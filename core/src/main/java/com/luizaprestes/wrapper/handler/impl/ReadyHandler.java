package com.luizaprestes.wrapper.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.entity.channel.TextChannel;
import com.luizaprestes.wrapper.entity.guild.Guild;
import com.luizaprestes.wrapper.entity.user.impl.SelfInfoImpl;
import com.luizaprestes.wrapper.event.listener.ReadyEvent;
import com.luizaprestes.wrapper.handler.SocketHandler;
import com.luizaprestes.wrapper.handler.client.EntityBuilder;
import com.luizaprestes.wrapper.util.Logger;

import java.util.ArrayList;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
public class ReadyHandler extends SocketHandler {

    private final WrapperClient client;
    private final EntityBuilder builder;

    private final Logger logger = new Logger(ReadyHandler.class, true);

    private final ObjectMapper map;

    public ReadyHandler(WrapperClient client, int responseNumber, EntityBuilder builder) {
        super(client, responseNumber);
        this.client = client;
        this.builder = builder;
        this.map = client.getMapper();
    }

    @Override
    public void handle(String context) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);

            client.setSelfInfo(builder.createSelfInfo(content.get("user").toPrettyString()));

            final ArrayNode guilds = (ArrayNode) content.get("guilds");
            final ArrayNode muted = (ArrayNode) content.get("muted_channels");
            final ArrayList<TextChannel> mutedChannels = new ArrayList<>();

            for (int i = 0; i < guilds.size(); i++) {
                final Guild guild = builder.createGuild(guilds.get(i).toPrettyString());
                final TextChannel channel = guild.getTextChannels().getChannelById(muted.get(i).textValue());

                if (channel != null) {
                    mutedChannels.add(channel);
                }
            }

            ((SelfInfoImpl) client.getSelfInfo()).setMutedChannels(mutedChannels);

            final ArrayNode privateChannels = (ArrayNode) content.get("private_channels");

            for (int i = 0; i < privateChannels.size(); i++) {
                builder.createPrivateChannel(privateChannels.get(i).toPrettyString());
            }

            client.getEventClient().handle(new ReadyEvent(client, responseNumber));
            logger.debug("ReadyHandler was loaded.");

        } catch (Exception exception) {
            logger.error("A error occurred while reading json from ready websocket message.");
            exception.printStackTrace();
        }

    }

}
