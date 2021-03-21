package com.luizaprestes.frame.view.command.impl;

import com.luizaprestes.frame.view.event.EventAdapter;
import com.luizaprestes.frame.view.event.EventType;
import com.luizaprestes.frame.view.event.guild.message.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class CommandListener {

    private final CommandParser parser;

    public CommandListener(CommandFrameImpl frame) {
        this.parser = new CommandParser(frame);
    }

    @EventAdapter(
      event = EventType.GUILD_MESSAGE_RECEIVED
    )
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        parser.parse(event.getMessage());
    }

}
