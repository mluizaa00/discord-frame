package com.luizaprestes.frame.factory;

import com.celeste.registries.Registry;
import com.celeste.registries.impl.ConcurrentRegistry;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.controller.GuildController;
import com.luizaprestes.frame.model.guild.Guild;
import lombok.Getter;

@Getter
public class GuildFactory {

    private final Registry<String, Guild> registry;
    private final GuildController controller;

    public GuildFactory(final Frame frame) {
        this.registry = new ConcurrentRegistry<>();
        this.controller = new GuildController(frame, this);
    }

}
