package com.luizaprestes.wrapper.entities.message;

import com.luizaprestes.wrapper.entities.channel.TextChannel;
import com.luizaprestes.wrapper.entities.user.User;

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
