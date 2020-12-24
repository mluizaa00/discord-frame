package com.luizaprestes.wrapper.entities.embed;

import java.sql.Timestamp;

public interface Embed {

    String getTitle();
    String getDescription();
    String getURL();

    Timestamp getTimeCreated();

    int getColor();

    // TODO:
    //  EmbedAuthor, EmbedField objects
    //  https://discord.com/developers/docs/resources/channel#embed-object
}
