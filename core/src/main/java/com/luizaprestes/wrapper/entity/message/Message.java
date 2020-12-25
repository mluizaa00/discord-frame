package com.luizaprestes.wrapper.entity.message;

import com.luizaprestes.wrapper.entity.channel.TextChannel;
import com.luizaprestes.wrapper.entity.user.User;

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
