package com.luizaprestes.example.listener;

import com.luizaprestes.frame.model.channel.TextChannel;
import com.luizaprestes.frame.model.guild.Guild;
import com.luizaprestes.frame.view.event.EventAdapter;
import com.luizaprestes.frame.view.event.EventType;
import com.luizaprestes.frame.view.event.channel.create.TextChannelCreateEvent;

/**
 @author luiza
 @version-implemented 0.0.2
 @date 01.07.2020
 */
public class ChannelCreateListener {

    @EventAdapter(event = EventType.TEXT_CHANNEL_CREATE)
    public void onTextChannelCreateEvent(TextChannelCreateEvent event) {
        final TextChannel channel = event.getChannel();
        final Guild guild = event.getGuild();
    }

}
