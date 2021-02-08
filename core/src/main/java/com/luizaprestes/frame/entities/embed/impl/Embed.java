package com.luizaprestes.frame.entities.embed.impl;

import com.luizaprestes.frame.entities.embed.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Embed implements IEmbed {

    private String title;
    private String description;
    private String URL;

    private Timestamp timeCreated;

    private int color;

    private Author author;
    private Content content;
    private Field field;
    private Footer footer;
    private Provider provider;

}
