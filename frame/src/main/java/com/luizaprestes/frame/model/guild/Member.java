package com.luizaprestes.frame.model.guild;

import com.luizaprestes.frame.model.Permission;
import com.luizaprestes.frame.model.user.User;
import com.luizaprestes.frame.model.OnlineStatus;
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
