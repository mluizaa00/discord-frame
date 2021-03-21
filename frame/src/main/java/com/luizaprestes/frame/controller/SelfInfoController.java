package com.luizaprestes.frame.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luizaprestes.frame.Frame;
import com.luizaprestes.frame.model.user.SelfUser;
import com.luizaprestes.frame.util.JacksonAdapter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class SelfInfoController {

    private final Frame frame;

    public SelfUser create(final String context) {
        final SelfUser selfInfo = frame.getSelfUser() != null ? frame.getSelfUser() : new SelfUser();
        frame.setSelfUser(selfInfo);

        try {
            final JacksonAdapter adapter = frame.getJacksonAdapter();
            final ObjectNode content = adapter.getNode(context);

            selfInfo.setVerified(adapter.getBoolean(content, "verified"));
            selfInfo.setUsername(adapter.getString(content, "username"));
            selfInfo.setAvatarId(content.get("avatar") != null ? adapter.getString(content, "avatar") : null);
        } catch (Exception exception) {
            frame.getLogger().atSevere().log(
                "A error occurred while creating SelfUser from Payload message. Value: %s",
                exception.getMessage()
            );
        }

        return selfInfo;
    }

}
