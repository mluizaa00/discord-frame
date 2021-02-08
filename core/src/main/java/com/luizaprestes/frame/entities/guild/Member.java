package com.luizaprestes.frame.entities.guild;

import com.luizaprestes.frame.enums.Permission;
import com.luizaprestes.frame.entities.user.User;
import com.luizaprestes.frame.enums.OnlineStatus;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Member {

    private final User user;
    private final Guild guild;

    private String displayName;
    private final String name;

    private OnlineStatus status;

    private List<Role> roles = new ArrayList<>();
    private List<Permission> permissions = new ArrayList<>();

    private final OffsetDateTime timeJoined;
    private OffsetDateTime timeBoosted;

    public boolean hasPermission(Permission[] permissions) {
        return false;
    }

}
