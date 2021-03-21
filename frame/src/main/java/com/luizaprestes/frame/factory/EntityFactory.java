package com.luizaprestes.frame.factory;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.channel.PrivateChannel;
import com.luizaprestes.frame.model.channel.VoiceChannel;
import com.luizaprestes.frame.model.channel.TextChannel;
import com.luizaprestes.frame.model.guild.Role;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.model.user.SelfUser;
import com.luizaprestes.frame.model.user.User;

/**
 @author luiza
 @since 12.19.2020
 */
public class EntityFactory {

    private final Frame frame;

    public EntityFactory(final Frame frame) {
        this.frame = frame;
    }

    public Guild createGuild(final String context) {
        return frame.getGuildFactory().getController().create(context);
    }

    public SelfUser createSelfInfo(final String context) {
        return frame.getSelfInfoController().create(context);
    }

    public Role createRole(final String context, final String guildId) {
        return frame.getRoleController().create(context, guildId);
    }

    public PrivateChannel createPrivateChannel(final String context) {
        return null;
    }

    public TextChannel createTextChannel(final String context, final String guildId) {
        return frame.getChannelController().createTextChannel(context, guildId);
    }

    public VoiceChannel createVoiceChannel(final String context, final String guildId) {
        return frame.getChannelController().createVoiceChannel(context, guildId);
    }

    public User createUser(final String context) {
        return frame.getUserFactory().getController().create(context);
    }

}
