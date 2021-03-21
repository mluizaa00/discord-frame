package com.luizaprestes.frame.view.command;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.view.command.message.MessageHolder;
import com.luizaprestes.frame.view.command.model.CommandModel;

public interface CommandFrame {

    String[] getPrefixes();

    CommandModel getCommand(String name);

    void loadCommands(Object... holders);

    void registerCommands(CommandModel... models);

    MessageHolder getMessageHolder();

    void build(Frame frame);

}
