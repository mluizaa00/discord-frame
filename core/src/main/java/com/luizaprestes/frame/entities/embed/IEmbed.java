package com.luizaprestes.frame.entities.embed;

import java.sql.Timestamp;

public interface IEmbed {

    String getTitle();

    String getDescription();

    String getURL();

    Timestamp getTimeCreated();

    int getColor();

    Author getAuthor();

    Content getContent();

    Field getField();

    Footer getFooter();

    Provider getProvider();

}
