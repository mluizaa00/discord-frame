package com.luizaprestes.frame.entities.message.impl;

import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.message.Message;
import com.luizaprestes.frame.entities.user.User;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MessageImpl implements Message {

    private String id;
    private User author;

    private String content;
    private TextChannel channel;

    private List<User> mentionedUsers;

    private boolean TTS;

    private Timestamp timeCreated;

    private Timestamp timeEdited = null;
    private boolean edited;

}
