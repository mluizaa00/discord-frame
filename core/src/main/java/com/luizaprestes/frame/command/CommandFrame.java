package com.luizaprestes.frame.command;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.command.message.MessageHolder;
import com.luizaprestes.frame.command.model.CommandModel;

public interface CommandFrame {

    String[] getPrefixes();

    CommandModel getCommand(String name);

    void loadCommands(Object... holders);

    void registerCommands(CommandModel... models);

    MessageHolder getMessageHolder();

    void build(Frame frame);

}
