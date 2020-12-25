package com.luizaprestes.wrapper.entity.guild.model;

import lombok.AllArgsConstructor;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@AllArgsConstructor
public enum Permission {

    CREATE_INSTANT_INVITE(0),
    KICK_MEMBERS(1),
    BAN_MEMBERS(2),
    MANAGE_ROLES(3),
    MANAGE_PERMISSIONS(3),
    MANAGE_CHANNEL(4),
    MANAGE_SERVER(5),

    MESSAGE_READ(10),
    MESSAGE_WRITE(11),
    MESSAGE_TTS(12),
    MESSAGE_MANAGE(13),
    MESSAGE_EMBED_LINKS(14),
    MESSAGE_ATTACH_FILES(15),
    MESSAGE_HISTORY(16),
    MESSAGE_MENTION_EVERYONE(17),

    VOICE_CONNECT(20),
    VOICE_SPEAK(21),
    VOICE_MUTE_OTHERS(22),
    VOICE_DEAF_OTHERS(23),
    VOICE_MOVE_OTHERS(24),
    VOICE_USE_VAD(25);

    private final int offset;
}
