package com.luizaprestes.wrapper.entities;

import com.luizaprestes.wrapper.entities.channel.PrivateChannel;
import com.luizaprestes.wrapper.entities.guild.Guild;
import com.luizaprestes.wrapper.entities.user.SelfInfo;
import com.luizaprestes.wrapper.entities.user.User;
import org.json.JSONObject;

public interface IEntity {

    Guild createGuild(JSONObject context);

    PrivateChannel createPrivateChannel(JSONObject context);

    SelfInfo createSelfInfo(JSONObject context);

    User createUser(JSONObject context);
}
