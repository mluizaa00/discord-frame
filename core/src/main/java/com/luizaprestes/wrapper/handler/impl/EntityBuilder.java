package com.luizaprestes.wrapper.handler.impl;

import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.entities.IEntity;
import com.luizaprestes.wrapper.entities.channel.PrivateChannel;
import com.luizaprestes.wrapper.entities.guild.Guild;
import com.luizaprestes.wrapper.entities.guild.Region;
import com.luizaprestes.wrapper.entities.guild.impl.GuildImpl;
import com.luizaprestes.wrapper.entities.user.SelfInfo;
import com.luizaprestes.wrapper.entities.user.User;
import com.luizaprestes.wrapper.entities.user.impl.SelfInfoImpl;
import com.luizaprestes.wrapper.entities.user.impl.UserImpl;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

@AllArgsConstructor
public class EntityBuilder implements IEntity {

    private final WrapperClient client;

    @Override
    public Guild createGuild(JSONObject context) {
        final String id = context.getString("id");
        GuildImpl guild = ((GuildImpl) client.getGuildRegistry().getGuildById(id));

        if (guild == null) {
            guild = new GuildImpl();
            client.getGuildRegistry().registerGuild(guild);
        }

        guild.setId(id);
        guild.setIconId(context.isNull("icon") ? null : context.getString("icon"));
        guild.setRegion(Region.getRegion(context.getString("region")));
        guild.setName(context.getString("name"));
        guild.setOwnerId(context.getString("owner_id"));
        guild.setAfkTimeout(context.getInt("afk_timeout"));
        guild.setAfkChannelId(context.isNull("afk_channel_id") ? null : context.getString("afk_channel_id"));

        final JSONArray members = context.getJSONArray("members");

        for (int i = 0; i < members.length(); i++) {
            final JSONObject member = members.getJSONObject(i);
            createUser(member.getJSONObject("user"));
        }

        return guild;
    }

    @Override
    public PrivateChannel createPrivateChannel(JSONObject context) {
        return null;
    }

    @Override
    public SelfInfo createSelfInfo(JSONObject context) {
        SelfInfoImpl selfInfo = ((SelfInfoImpl) client.getSelfInfo());

        if (selfInfo == null) {
            selfInfo = new SelfInfoImpl();
            client.setSelfInfo(selfInfo);
        }

        selfInfo.setVerified(context.getBoolean("verified"));
        selfInfo.setUsername(context.getString("username"));
        selfInfo.setId(context.getString("id"));
        selfInfo.setEmail(context.getString("email"));
        selfInfo.setAvatarId(context.getString("avatar"));

        return selfInfo;
    }

    @Override
    public User createUser(JSONObject context) {
        final String id = context.getString("id");

        UserImpl user = (UserImpl) client.getUserRegistry().getUserById(id);
        if (user == null) {
            user = new UserImpl();
            client.getUserRegistry().registerUser(user);
        }

        user.setId(id);
        user.setUsername(context.getString("username"));
        user.setAvatarId(context.getString("avatar"));

        return user;
    }
}
