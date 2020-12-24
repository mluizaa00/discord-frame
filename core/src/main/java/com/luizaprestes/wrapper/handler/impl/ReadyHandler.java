package com.luizaprestes.wrapper.handler.impl;

import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.entities.channel.TextChannel;
import com.luizaprestes.wrapper.entities.guild.Guild;
import com.luizaprestes.wrapper.entities.user.impl.SelfInfoImpl;
import com.luizaprestes.wrapper.event.listener.ReadyEvent;
import com.luizaprestes.wrapper.handler.ISocketHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
public class ReadyHandler implements ISocketHandler {

    private final WrapperClient client;
    private final EntityBuilder builder;

    public ReadyHandler(WrapperClient client, EntityBuilder builder) {
        this.client = client;
        this.builder = builder;
    }

    @Override
    public void handle(JSONObject context) {
        client.setSelfInfo(builder.createSelfInfo(context.getJSONObject("user")));

        final List<TextChannel> mutedChannels = new ArrayList<>();

        final JSONArray guilds = context.getJSONArray("guilds");
        final JSONArray muted = context.getJSONObject("user_settings").getJSONArray("muted_channels");

        for (int i = 0; i < guilds.length(); i++) {
            final Guild guild = builder.createGuild(guilds.getJSONObject(i));
            final TextChannel channel = guild.getTextChannels().getChannelById(muted.getString(i));

            if (channel != null) {
                mutedChannels.add(channel);
            }
        }

        ((SelfInfoImpl) client.getSelfInfo()).setMutedChannels(mutedChannels);

        final JSONArray privateChannels = context.getJSONArray("private_channels");
        for (int i = 0; i < privateChannels.length(); i++) {
            builder.createPrivateChannel(privateChannels.getJSONObject(i));
        }

        client.getEventClient().handle(new ReadyEvent());
        System.out.println("ReadyHandler was loaded.");
    }

}
