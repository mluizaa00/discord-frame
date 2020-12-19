package com.luizaprestes.wrapper.handler.event;

import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.entities.guild.Guild;
import com.luizaprestes.wrapper.handler.ISocketHandler;
import com.luizaprestes.wrapper.handler.impl.EntityBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

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

        final JSONArray guilds = context.getJSONArray("guilds");

        for (int i = 0; i < guilds.length(); i++) {
            final Guild guild = builder.createGuild(guilds.getJSONObject(i));
            client.getGuildRegistry().registerGuild(guild);
        }
    }
}
