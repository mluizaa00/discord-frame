package com.luizaprestes.frame.command.impl;

import com.luizaprestes.frame.command.CommandFrame;
import com.luizaprestes.frame.command.message.MessageType;
import com.luizaprestes.frame.command.model.CommandModel;
import com.luizaprestes.frame.entities.guild.model.Permission;
import com.luizaprestes.frame.entities.guild.model.Role;
import com.luizaprestes.frame.entities.member.Member;
import com.luizaprestes.frame.entities.message.Message;

public class CommandParser {

    private final CommandFrame frame;

    public CommandParser(CommandFrame frame) {
        this.frame = frame;
    }

    public void parse(final Message message) {
        final String content = message.getContent();

        String prefix = null;

        for (String s : frame.getPrefixes()) {
            if (content.startsWith(s)) {
                prefix = s;
                break;
            }
        }

        if (prefix == null || !content.startsWith(prefix)) return;

        final boolean containsSpace = content.contains(" ");
        final String label = content.substring(prefix.length(), containsSpace ? content.indexOf(" ") : content.length());

        final CommandModel command = frame.getCommand(label);
        if (command == null) return;

        if (!test(message.getMember(), command.getPermissions())
          || test(message.getMember(), command.getRole())
        ) {
            message.reply(frame.getMessageHolder().getMessage(MessageType.LACK_PERM_MESSAGE));
            return;
        }

        final String[] args = containsSpace ? content.substring(prefix.length()
          + label.length() + 1).split(" ") : new String[0];

        command.onCommand(message, args);
    }

    private boolean test(final Member member, final Permission[] permissions) {
        return member.hasPermission(permissions);
    }

    private boolean test(final Member member, final long role) {
        for (Role memberRole : member.getRoles()) {
            if (Long.parseLong(memberRole.getId()) == role) {
                return true;
            }
        }

        return false;
    }
}
