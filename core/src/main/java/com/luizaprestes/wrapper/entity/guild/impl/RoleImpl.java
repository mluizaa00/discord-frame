package com.luizaprestes.wrapper.entity.guild.impl;

import com.luizaprestes.wrapper.entity.guild.model.Permission;
import com.luizaprestes.wrapper.entity.guild.model.Role;
import lombok.Data;

@Data
public class RoleImpl implements Role {

    private final String id;
    private String name;
    private int position;

    private int color;

    private boolean hoist;
    private boolean managed;
    private boolean mentionable;

    private int permissions;

    @Override
    public boolean hasPermission(Permission permission) {
        return false;
    }
}
