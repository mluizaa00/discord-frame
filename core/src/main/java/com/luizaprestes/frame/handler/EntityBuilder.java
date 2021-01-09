package com.luizaprestes.frame.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.channel.impl.VoiceChannelImpl;
import com.luizaprestes.frame.entities.channel.PrivateChannel;
import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.channel.VoiceChannel;
import com.luizaprestes.frame.entities.channel.impl.TextChannelImpl;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.guild.impl.RoleImpl;
import com.luizaprestes.frame.entities.guild.model.Region;
import com.luizaprestes.frame.entities.guild.impl.GuildImpl;
import com.luizaprestes.frame.entities.guild.model.Role;
import com.luizaprestes.frame.entities.user.SelfInfo;
import com.luizaprestes.frame.entities.user.User;
import com.luizaprestes.frame.entities.user.impl.SelfInfoImpl;
import com.luizaprestes.frame.entities.user.impl.UserImpl;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
public class EntityBuilder implements IEntity {

    private final Frame client;
    private final ObjectMapper map;

    public EntityBuilder(Frame client) {
        this.client = client;
        this.map = client.getMapper();
    }

    @Override
    public Guild createGuild(String context) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);

            if (!content.get("unavailable").booleanValue()) {
                final String id = content.get("id").textValue();
                GuildImpl guild = ((GuildImpl) client.getGuildRegistry().getGuildById(id));

                if (guild == null) {
                    guild = new GuildImpl(id);
                    client.getGuildRegistry().registerGuild(guild);
                }

                guild.setIconId(content.get("icon") != null ? content.get("icon").textValue() : null);
                guild.setRegion(Region.getRegion(content.get("region").textValue()));
                guild.setName(content.get("name").textValue());
                guild.setOwnerId(content.get("owner_id").textValue());
                guild.setAfkTimeout(content.get("afk_timeout").intValue());
                guild.setAfkChannelId(content.get("afk_channel_id") != null ? content.get("afk_channel_id").textValue() : null);

                final ArrayNode channels = (ArrayNode) content.get("channels");
                for (int i = 0; i < channels.size(); i++) {
                    final ObjectNode channel = map.readValue(channels.get(i).toPrettyString(), ObjectNode.class);
                    final String type = channel.get("type").textValue();

                    if (type == null) break;
                    switch (type) {
                        case "text": {
                            createTextChannel(context, id);
                        }
                        case "voice": {
                            createVoiceChannel(context, id);
                        }
                    }

                }

                final ArrayNode roles = (ArrayNode) content.get("roles");
                for (int i = 0; i < roles.size(); i++) {
                    final ObjectNode roleObject = map.readValue(roles.get(i).toPrettyString(), ObjectNode.class);
                    final Role role = createRole(roleObject.toPrettyString(), id);

                    guild.getRolesList().add(role);
                    guild.getRoles().registerRole(role);
                }

                final ArrayNode members = (ArrayNode) content.get("members");
                for (int i = 0; i < members.size(); i++) {
                    final ObjectNode member = map.readValue(members.get(i).get("user").toPrettyString(), ObjectNode.class);
                    createUser(member.toPrettyString());
                }

                return guild;
            }
        } catch(Exception exception) {
            client.getLogger().atSevere().log(
              "A error occurred while creating Guild from Payload message. Value: %s", exception.getMessage());
        }

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
            selfInfo.setAvatarId(content.get("avatar") != null ? content.get("avatar").textValue() : null);
        } catch (Exception exception) {
            client.getLogger().atSevere().log(
              "A error occurred while creating SelfInfo from Payload message. Value: %s", exception.getMessage());
        }

        return selfInfo;
    }

    @Override
    public Role createRole(String context, String guildId) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);
            final GuildImpl guild = (GuildImpl) client.getGuildRegistry().getGuildById(guildId);

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
            client.getLogger().atSevere().log(
              "A error occurred while creating Role from Payload message. Value: %s", exception.getMessage());
        }

        return null;
    }

    @Override
    public PrivateChannel createPrivateChannel(String context) {
        return null;
    }

    @Override
    public TextChannel createTextChannel(String context, String guildId) {
        try {
            final GuildImpl guild = (GuildImpl) client.getGuildRegistry().getGuildById(guildId);
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
            client.getLogger().atSevere().log(
              "A error occurred while creating TextChannel from Payload message. Value: %s", exception.getMessage());
        }

        return null;
    }

    @Override
    public VoiceChannel createVoiceChannel(String context, String guildId) {
        try {
            final ObjectNode content = map.readValue(context, ObjectNode.class);
            final GuildImpl guild = (GuildImpl) client.getGuildRegistry().getGuildById(guildId);

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
            client.getLogger().atSevere().log(
              "A error occurred while creating VoiceChannel from Payload message. Value: %s", exception.getMessage());
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
        } catch (JsonProcessingException exception) {
            client.getLogger().atSevere().log(
              "A error occurred while creating User from Payload message. Value: %s", exception.getMessage());
        }

        return null;
    }

}
