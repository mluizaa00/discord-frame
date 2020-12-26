package com.luizaprestes.wrapper.handler.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.entity.channel.impl.VoiceChannelImpl;
import com.luizaprestes.wrapper.handler.IEntity;
import com.luizaprestes.wrapper.entity.channel.PrivateChannel;
import com.luizaprestes.wrapper.entity.channel.TextChannel;
import com.luizaprestes.wrapper.entity.channel.VoiceChannel;
import com.luizaprestes.wrapper.entity.channel.impl.TextChannelImpl;
import com.luizaprestes.wrapper.entity.guild.Guild;
import com.luizaprestes.wrapper.entity.guild.impl.RoleImpl;
import com.luizaprestes.wrapper.entity.guild.model.Region;
import com.luizaprestes.wrapper.entity.guild.impl.GuildImpl;
import com.luizaprestes.wrapper.entity.guild.model.Role;
import com.luizaprestes.wrapper.entity.user.SelfInfo;
import com.luizaprestes.wrapper.entity.user.User;
import com.luizaprestes.wrapper.entity.user.impl.SelfInfoImpl;
import com.luizaprestes.wrapper.entity.user.impl.UserImpl;
import com.luizaprestes.wrapper.util.Logger;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
public class EntityBuilder implements IEntity {

    private final WrapperClient client;
    private final ObjectMapper map;
    private final Logger logger;

    public EntityBuilder(WrapperClient client) {
        this.client = client;
        this.map = client.getMapper();
        this.logger = new Logger(EntityBuilder.class, true);
    }

    @Override
    public Guild createGuild(String context) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);

            final String id = content.get("id").textValue();
            GuildImpl guild = ((GuildImpl) client.getGuildRegistry().getGuildById(id));

            if (guild == null) {
                guild = new GuildImpl(id);
                client.getGuildRegistry().registerGuild(guild);
            }

            guild.setIconId(content.get("icon").textValue());
            guild.setRegion(Region.getRegion(content.get("region").textValue()));
            guild.setName(content.get("name").textValue());
            guild.setOwnerId(content.get("owner_id").textValue());
            guild.setAfkTimeout(content.get("afk_timeout").intValue());
            guild.setAfkChannelId(content.get("afk_channel_id").textValue());

            final ArrayNode channels = (ArrayNode) content.get("channels");
            for (int i = 0; i < channels.size(); i++) {
                final ObjectNode channel = map.readValue(channels.get(i).toPrettyString(), ObjectNode.class);
                final String type = channel.get("type").textValue();

                switch (type) {
                    case "text": {
                        createTextChannel(context, guild);
                    }
                    case "voice": {
                        createVoiceChannel(context, guild);
                    }
                }

            }

            final ArrayNode roles = (ArrayNode) content.get("roles");
            for (int i = 0; i < roles.size(); i++) {
                final ObjectNode roleObject = map.readValue(roles.get(i).toPrettyString(), ObjectNode.class);
                final Role role = createRole(roleObject.toPrettyString(), guild);

                guild.getRolesList().add(role);
                guild.getRoles().registerRole(role);
            }

            final ArrayNode members = (ArrayNode) content.get("members");
            for (int i = 0; i < members.size(); i++) {
                final ObjectNode member = map.readValue(members.get(i).toPrettyString(), ObjectNode.class);
                createUser(member.toPrettyString());
            }

            return guild;
        } catch (Exception exception) {
            logger.error("A error occurred while creating guild from json message.");
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public PrivateChannel createPrivateChannel(String context) {
        return null;
    }

    @Override
    public SelfInfo createSelfInfo(String context) {
        SelfInfoImpl selfInfo = ((SelfInfoImpl) client.getSelfInfo());

        if (selfInfo == null) {
            selfInfo = new SelfInfoImpl();
            client.setSelfInfo(selfInfo);
        }

        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);

            selfInfo.setVerified(content.get("verified").booleanValue());
            selfInfo.setUsername(content.get("username").textValue());
            selfInfo.setAvatarId(content.get("avatar").textValue());
        } catch (Exception exception) {
            logger.error("A error occurred while creating selfinfo from json message.");
            exception.printStackTrace();
        }

        return selfInfo;
    }

    @Override
    public Role createRole(String context, GuildImpl guild) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);

            final String id = content.get("id").textValue();
            RoleImpl role = ((RoleImpl) guild.getRoles().getRoleById(id));

            if (role == null) role = new RoleImpl(id);

            role.setName(content.get("name").textValue());
            role.setPosition(content.get("position").intValue());
            role.setPermissions(content.get("permissions").intValue());
            role.setManaged(content.get("managed").booleanValue());
            role.setHoist(content.get("hoist").booleanValue());
            role.setColor(content.get("color").intValue());

            return role;
        } catch (Exception exception) {
            logger.error("A error occurred while creating role from json message.");
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public TextChannel createTextChannel(String context, GuildImpl guild) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);

            final String id = content.get("id").textValue();
            TextChannelImpl channel = (TextChannelImpl) guild.getTextChannels().getChannelById(id);

            if (channel == null) {
                channel = new TextChannelImpl(id, guild);
                guild.getTextChannels().registerChannel(channel);
            }

            channel.setName(content.get("name").textValue());
            channel.setTopic(content.get("topic").textValue());

            channel.setPosition(content.get("position").intValue());

            return channel;
        } catch (Exception exception) {
            logger.error("A error occurred while creating textchannel from json message.");
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public VoiceChannel createVoiceChannel(String context, GuildImpl guild) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);

            final String id = content.get("id").textValue();
            VoiceChannelImpl channel = (VoiceChannelImpl) guild.getVoiceChannels().getChannelById(id);

            if (channel == null) {
                channel = new VoiceChannelImpl(id, guild);
                guild.getVoiceChannels().registerChannel(channel);
            }

            channel.setName(content.get("name").textValue());
            channel.setUserLimit(content.get("user_limit").intValue());
            channel.setBitrate(content.get("bitrate").intValue());
            channel.setUsers(null);

            return channel;
        } catch (Exception exception) {
            logger.error("A error occurred while creating voicechannel from json message.");
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public User createUser(String context) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);

            final String id = content.get("id").textValue();
            UserImpl user = (UserImpl) client.getUserRegistry().getUserById(id);

            if (user == null) {
                user = new UserImpl(id);
                client.getUserRegistry().registerUser(user);
            }

            user.setUsername(content.get("username").textValue());
            user.setAvatarId(content.get("avatar").textValue());

            return user;
        } catch (Exception exception) {
            logger.error("A error occurred while creating user from json message.");
            exception.printStackTrace();
        }

        return null;
    }

}
