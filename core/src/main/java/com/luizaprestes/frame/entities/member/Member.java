package com.luizaprestes.frame.entities.member;

import com.luizaprestes.frame.entities.guild.Guild;
import com.luizaprestes.frame.entities.guild.model.Permission;
import com.luizaprestes.frame.entities.guild.model.Role;
import com.luizaprestes.frame.entities.user.User;
import com.luizaprestes.frame.entities.user.model.OnlineStatus;

import java.time.OffsetDateTime;
import java.util.List;

public interface Member {

    User getUser();

    Guild getGuild();

    OffsetDateTime getTimeJoined();

    OffsetDateTime getTimeBoosted();

    OnlineStatus getOnlineStatus();

    String getNickname();

    String getName();

    List<Role> getRoles();

    List<Permission> getPermissions();

    boolean hasPermission(Permission[] permissions);
}
