package com.luizaprestes.frame;

import com.google.common.flogger.FluentLogger;
import com.luizaprestes.frame.controller.ChannelController;
import com.luizaprestes.frame.controller.PacketController;
import com.luizaprestes.frame.controller.RoleController;
import com.luizaprestes.frame.controller.SelfInfoController;
import com.luizaprestes.frame.factory.GuildFactory;
import com.luizaprestes.frame.factory.UserFactory;
import com.luizaprestes.frame.model.user.SelfUser;
import com.luizaprestes.frame.model.OnlineStatus;
import com.luizaprestes.frame.util.JacksonAdapter;
import com.luizaprestes.frame.view.event.client.EventClient;
import com.luizaprestes.frame.view.event.client.EventLoader;
import com.luizaprestes.frame.gateway.GatewayStatus;
import com.luizaprestes.frame.factory.EntityFactory;
import com.luizaprestes.frame.util.Configuration;
import com.luizaprestes.frame.util.Constants;
import com.luizaprestes.frame.gateway.PacketHandler;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

/**
@author luiza
@since 12.18.2020

The Frame class is the base of creating the wrapper, it contains all infos necessary to iniciate
the client.
 */
@Getter
public class Frame {

    @Setter
    private GatewayStatus gatewayStatus;

    private final PacketHandler packetHandler;

    private final JacksonAdapter jacksonAdapter = new JacksonAdapter();
    private final OkHttpClient httpClient = new OkHttpClient();

    private final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final EventClient eventClient;
    private final EventLoader eventLoader;

    private final SelfInfoController selfInfoController;
    private final RoleController roleController;
    private final ChannelController channelController;
    private final PacketController packetController;

    private final GuildFactory guildFactory;
    private final EntityFactory entityFactory;
    private final UserFactory userFactory;

    private final String token;
    private final OnlineStatus onlineStatus;

    private final Configuration configuration;

    @Setter
    private boolean connected;

    @Setter
    private SelfUser selfUser;

    public Frame(@NotNull final String token) {
        this.token = token;

        this.gatewayStatus = GatewayStatus.LOADING;
        this.packetHandler = new PacketHandler(Constants.GATEWAY, this);

        this.eventClient = new EventClient();
        this.eventLoader = new EventLoader(this);

        this.selfInfoController = new SelfInfoController(this);
        this.roleController = new RoleController(this);
        this.channelController = new ChannelController(this, jacksonAdapter);
        this.packetController = new PacketController(this);

        this.guildFactory = new GuildFactory(this);
        this.entityFactory = new EntityFactory(this);
        this.userFactory = new UserFactory(this);

        this.configuration = new Configuration(this, "config");

        this.onlineStatus = getOnlineStatus() != null ? getOnlineStatus() : OnlineStatus.ONLINE;
    }

    public boolean connect() {
        if (!connected) {
            packetHandler.connect();
            return true;
        }

        return false;
    }

    public boolean shutdown() {
        if (connected) {
            packetHandler.close();
            return true;
        }

        return false;
    }

}
