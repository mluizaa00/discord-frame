package com.luizaprestes.frame.command.impl;

import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.command.CommandFrame;
import com.luizaprestes.frame.command.message.MessageHolder;
import com.luizaprestes.frame.command.model.CommandModel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CommandFrameImpl implements CommandFrame {

    private final String[] prefixes;
    private final Set<CommandModel> commandSet;

    private final CommandLoader loader;
    private final MessageHolder holder;

    public CommandFrameImpl(String[] prefixes) {
        this.prefixes = prefixes;
        this.commandSet = new HashSet<>();

        this.loader = new CommandLoader();
        this.holder = new MessageHolder();
    }

    @Override
    public String[] getPrefixes() {
        return this.prefixes;
    }

    @Override
    public CommandModel getCommand(String name) {
        for (CommandModel model : commandSet) {
            if (model.getName().equalsIgnoreCase(name)) {
                return model;
            }

            for (String alias : model.getAliases()) {
                if(alias.equalsIgnoreCase(name)) {
                    return model;
                }
            }
        }

        return null;
    }

    @Override
    public void loadCommands(Object... holders) {
        for (Object holder : holders) {
            System.out.println(holder.getClass().getSimpleName());

            loader.load(this, holder);
        }
    }

    @Override
    public void registerCommands(CommandModel... models) {
        commandSet.addAll(Arrays.asList(models));
    }

    @Override
    public MessageHolder getMessageHolder() {
        return holder;
    }

    @Override
    public void build(Frame frame) {
        frame.getEventClient().register(new CommandListener(this));
    }
}
