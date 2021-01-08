package com.luizaprestes.frame.entities.guild.registry;

import com.luizaprestes.frame.entities.guild.model.Role;

import java.util.Collection;
import java.util.HashMap;

public class RoleRegistry {

    private final HashMap<String, Role> roles;

    public RoleRegistry() {
        this.roles = new HashMap<>();
    }

    public void registerRole(final Role role) {
        roles.put(role.getId(), role);
    }

    public Role getRoleById(final String roleId) {
        return roles.get(roleId);
    }

    public void remove(final String guildId) {
        roles.remove(guildId);
    }

    public Collection<Role> getAll() {
        return roles.values();
    }

}
