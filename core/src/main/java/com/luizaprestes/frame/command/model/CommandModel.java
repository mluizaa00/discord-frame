package com.luizaprestes.frame.command.model;

import com.luizaprestes.frame.entities.guild.model.Permission;
import com.luizaprestes.frame.entities.message.Message;
import lombok.Data;
@Data
public abstract class CommandModel {

    private final String name;
    private final String[] aliases;

    private final Permission[] permissions;
    private final long role;

    public abstract void onCommand(final Message context, final String[] args);
}
