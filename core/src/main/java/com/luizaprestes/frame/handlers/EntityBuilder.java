package com.luizaprestes.frame.handlers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.entities.channel.PrivateChannel;
import com.luizaprestes.frame.entities.channel.VoiceChannel;
import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.guild.Role;
import com.luizaprestes.frame.enums.Region;
import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.user.SelfUser;
import com.luizaprestes.frame.entities.user.User;
import com.luizaprestes.frame.utils.JacksonAdapter;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
public class EntityBuilder implements IEntity {

    private final Frame frame;
    private final JacksonAdapter adapter;

    public EntityBuilder(Frame frame) {
        this.frame = frame;
        this.adapter = frame.getJacksonAdapter();
    }

    @Override
    public Guild createGuild(String context) {
        try {
            final ObjectNode content = adapter.getNode(context);

            if (adapter.getBoolean(content, "unavailable")) return null;

            final String id = adapter.getString(content, "id");
            final Guild guild = frame.getGuildRegistry().getByValue(id) != null ? frame.getGuildRegistry().getByValue(id) : new Guild(id);

            guild.setIconId(content.get("icon") != null ? adapter.getString(content, "icon") : null);
            guild.setRegion(Region.getById(adapter.getString(content, "region")));
            guild.setName(adapter.getString(content, "name"));
            guild.setOwnerId(adapter.getString(content, "owner_id"));
            guild.setAfkTimeout(adapter.getInt(content, "afk_timeout"));
            guild.setAfkChannelId(content.get("afk_channel_id") != null ? adapter.getString(content, "afk_channel_id") : null);

            final ArrayNode channels = (ArrayNode) content.get("channels");
            for (int i = 0; i < channels.size(); i++) {
                final ObjectNode channel = adapter.getNode(channels.get(i).toPrettyString());
                final String type = adapter.getString(channel, "type");

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
                final ObjectNode roleObject = adapter.getNode(roles.get(i).toPrettyString());
                final Role role = createRole(roleObject.toPrettyString(), id);

                guild.getRoles().register(role.getId(), role);
            }

            final ArrayNode members = (ArrayNode) content.get("members");
            for (int i = 0; i < members.size(); i++) {
                final ObjectNode member = adapter.getNode(members.get(i).get("user").toPrettyString());
                createUser(member.toPrettyString());
            }

            frame.getGuildRegistry().register(guild.getId(), guild);
            return guild;
        } catch (Exception exception) {
            frame.getLogger().atSevere().log(
              "A error occurred while creating Guild from Payload message. Value: %s",
              exception.getMessage()
            );
        }

        return null;
    }

    @Override
    public SelfUser createSelfInfo(String context) {
        final SelfUser selfInfo = frame.getSelfUser() != null ? frame.getSelfUser() : new SelfUser();
        frame.setSelfUser(selfInfo);

        try {
            final ObjectNode content = adapter.getNode(context);

            selfInfo.setVerified(adapter.getBoolean(content, "verified"));
            selfInfo.setUsername(adapter.getString(content, "username"));
            selfInfo.setAvatarId(content.get("avatar") != null ? adapter.getString(content, "avatar") : null);
        } catch (Exception exception) {
            frame.getLogger().atSevere().log(
              "A error occurred while creating SelfUser from Payload message. Value: %s",
              exception.getMessage()
            );
        }

        return selfInfo;
    }

    @Override
    public Role createRole(String context, String guildId) {
        try {
            final ObjectNode content = adapter.getNode(context);
            final Guild guild = frame.getGuildRegistry().getByValue(guildId);

            final String id = adapter.getString(content, "id");
            final Role role = guild.getRoles().getByValue(id) != null ? guild.getRoles().getByValue(id) : new Role(id);

            role.setName(adapter.getString(content, "name"));
            role.setPosition(adapter.getInt(content, "position"));
            role.setPermissions(adapter.getInt(content, "permissions"));
            role.setManaged(adapter.getBoolean(content, "managed"));
            role.setHoist(adapter.getBoolean(content, "hoist"));
            role.setColor(adapter.getInt(content, "color"));

            guild.getRoles().register(role.getId(), role);
            return role;
        } catch (Exception exception) {
            frame.getLogger().atSevere().log(
              "A error occurred while creating Role from Payload message. Value: %s",
              exception.getMessage()
            );
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
            final Guild guild = frame.getGuildRegistry().getByValue(guildId);
            final ObjectNode content = adapter.getNode(context);

            final String id = adapter.getString(content, "id");
            final TextChannel channel = guild.getTextChannels().getByValue(id) != null ? guild.getTextChannels().getByValue(id) : new TextChannel(id, guild);

            channel.setName(adapter.getString(content, "name"));
            channel.setTopic(adapter.getString(content, "topic"));
            channel.setPosition(adapter.getInt(content, "position"));

            guild.getTextChannels().register(channel.getId(), channel);
            return channel;
        } catch (Exception exception) {
            frame.getLogger().atSevere().log(
              "A error occurred while creating TextChannel from Payload message. Value: %s",
              exception.getMessage()
            );
        }

        return null;
    }

    @Override
    public VoiceChannel createVoiceChannel(String context, String guildId) {
        try {
            final ObjectNode content = adapter.getNode(context);
            final Guild guild = frame.getGuildRegistry().getByValue(guildId);

            final String id = adapter.getString(content, "id");
            final VoiceChannel channel = guild.getVoiceChannels().getByValue(id) != null ? guild.getVoiceChannels().getByValue(id) : new VoiceChannel(id, guild);

            channel.setName(adapter.getString(content, "name"));
            channel.setUserLimit(adapter.getInt(content, "user_limit"));
            channel.setBitrate(adapter.getInt(content, "bitrate"));

            guild.getVoiceChannels().register(channel.getId(), channel);
            return channel;
        } catch (Exception exception) {
            frame.getLogger().atSevere().log(
              "A error occurred while creating VoiceChannel from Payload message. Value: %s",
              exception.getMessage()
            );
        }

        return null;
    }

    @Override
    public User createUser(String context) {
        final ObjectNode content = adapter.getNode(context);
        final String id = adapter.getString(content, "id");

        final User user = frame.getUserRegistry().getByValue(id) != null ? frame.getUserRegistry().getByValue(id) : new User(id);

        user.setUsername(adapter.getString(content, "username"));
        user.setAvatarId(adapter.getString(content, "avatar"));

        frame.getUserRegistry().register(user.getId(), user);
        return user;
    }

}
