package com.luizaprestes.frame.entities;

import com.luizaprestes.frame.entities.channel.TextChannel;
import com.luizaprestes.frame.entities.guild.Member;
import com.luizaprestes.frame.entities.user.User;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Message {

    private final String id;
    private final User author;
    private final Member member;

    private String content;
    private final TextChannel channel;

    private List<User> mentionedUsers;

    private boolean TTS;

    private Timestamp timeCreated;

    private Timestamp timeEdited = null;
    private boolean edited;

    public void reply(String message) {
    }

}
