package com.luizaprestes.wrapper.handler.impl;

import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.handler.IEntity;
import com.luizaprestes.wrapper.entities.channel.PrivateChannel;
import com.luizaprestes.wrapper.entities.channel.TextChannel;
import com.luizaprestes.wrapper.entities.channel.VoiceChannel;
import com.luizaprestes.wrapper.entities.channel.impl.TextChannelImpl;
import com.luizaprestes.wrapper.entities.guild.Guild;
import com.luizaprestes.wrapper.entities.guild.impl.RoleImpl;
import com.luizaprestes.wrapper.entities.guild.model.Region;
import com.luizaprestes.wrapper.entities.guild.impl.GuildImpl;
import com.luizaprestes.wrapper.entities.guild.model.Role;
import com.luizaprestes.wrapper.entities.user.SelfInfo;
import com.luizaprestes.wrapper.entities.user.User;
import com.luizaprestes.wrapper.entities.user.impl.SelfInfoImpl;
import com.luizaprestes.wrapper.entities.user.impl.UserImpl;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@AllArgsConstructor
public class EntityBuilder implements IEntity {

    private final WrapperClient client;

    @Override
    public Guild createGuild(JSONObject context) {
        final String id = context.getString("id");
        GuildImpl guild = ((GuildImpl) client.getGuildRegistry().getGuildById(id));

        if (guild == null) {
            guild = new GuildImpl(id);
            client.getGuildRegistry().registerGuild(guild);
        }

        guild.setIconId(context.isNull("icon") ? null : context.getString("icon"));
        guild.setRegion(Region.getRegion(context.getString("region")));
        guild.setName(context.getString("name"));
        guild.setOwnerId(context.getString("owner_id"));
        guild.setAfkTimeout(context.getInt("afk_timeout"));
        guild.setAfkChannelId(context.isNull("afk_channel_id") ? null : context.getString("afk_channel_id"));

        final JSONArray channels = context.getJSONArray("channels");
        for (int i = 0; i < channels.length(); i++) {
            final JSONObject channel = channels.getJSONObject(i);
            final String type = channel.getString("type");

            switch (type) {
                case "text": {
                    createTextChannel(context, guild);
                }
                case "voice": {
                    createVoiceChannel(context);
                }
            }
        }

        final JSONArray roles = context.getJSONArray("roles");
        for (int i = 0; i < roles.length(); i++) {
            final Role role = createRole(roles.getJSONObject(i), guild);

            guild.getRolesList().add(role);
            guild.getRoles().registerRole(role);
        }

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

        selfInfo.setAvatarId(context.isNull("avatar") ? null : context.getString("avatar"));

        return selfInfo;
    }

    @Override
    public Role createRole(JSONObject context, GuildImpl guild) {
        final String id = context.getString("id");
        RoleImpl role = ((RoleImpl) guild.getRoles().getRoleById(id));

        if (role == null) role = new RoleImpl(id);

        role.setName(context.getString("name"));
        role.setPosition(context.getInt("position"));
        role.setPermissions(context.getInt("permissions"));
        role.setManaged(context.getBoolean("managed"));
        role.setHoist(context.getBoolean("hoist"));
        role.setColor(context.getInt("color"));

        return role;
    }

    @Override
    public TextChannel createTextChannel(JSONObject context, GuildImpl guild) {
        final String id = context.getString("id");
        TextChannelImpl channel = (TextChannelImpl) guild.getTextChannels().getChannelById(id);

        if (channel == null) {
            channel = new TextChannelImpl(id, guild);
            guild.getTextChannels().registerChannel(channel);
        }

        channel.setName(context.getString("name"));

        channel.setTopic(context.isNull("topic") ? null : context.getString("topic"));
        channel.setPosition(context.getInt("position"));

        return channel;
    }

    @Override
    public VoiceChannel createVoiceChannel(JSONObject context) {
        return null;
    }

    @Override
    public User createUser(JSONObject context) {
        final String id = context.getString("id");

        UserImpl user = (UserImpl) client.getUserRegistry().getUserById(id);
        if (user == null) {
            user = new UserImpl(id);
            client.getUserRegistry().registerUser(user);
        }

        user.setUsername(context.getString("username"));
        user.setAvatarId(context.getString("avatar"));

        return user;
    }

}
