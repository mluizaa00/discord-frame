package com.luizaprestes.frame.model.guild;

import com.luizaprestes.frame.model.Permission;
import lombok.Data;

@Data
public class Role {

    private final String id;
    private String name;
    private int position;

    private int color;

    private boolean hoist;
    private boolean managed;
    private boolean mentionable;

    private int permissions;

    public boolean hasPermission(Permission permission) {
        return false;
    }

}
