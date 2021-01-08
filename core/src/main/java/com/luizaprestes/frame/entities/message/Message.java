package com.luizaprestes.frame.entities.message;

import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.user.User;

import java.sql.Timestamp;
import java.util.List;

public interface Message {

    String getId();

    User getAuthor();

    String getContent();

    TextChannel getChannel();

    List<User> getMentionedUsers();

    Timestamp getTimeCreated();

    boolean isEdited();

    Timestamp getTimeEdited();

    boolean isTTS();

}
