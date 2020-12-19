package com.luizaprestes.wrapper;

import com.luizaprestes.wrapper.http.WebSocketClientImpl;
import com.luizaprestes.wrapper.http.requests.RequestManager;
import com.luizaprestes.wrapper.http.requests.RequestType;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Getter
public class Wrapper {

    protected static String email;
    protected static String password;

    private String authToken;
    private final WebSocketClientImpl client;
    private String gateway;

    public Wrapper() throws LoginException {
        if (email == null || password == null)
            throw new IllegalArgumentException("The provided email or password is null.");

        try {
            final RequestManager managerTwo = new RequestManager(this);

            managerTwo.setType(RequestType.GET);
            managerTwo.setUrl("https://discordapp.com/api/gateway");

            authToken = getConfig().getString("token");

            this.gateway = new JSONObject(managerTwo.makeRequest()).getString("url");

            System.out.println("Gateway token: " + authToken);
        } catch (Exception exception) {
            // not a problem
        }

        if (gateway == null) {
            final RequestManager manager = new RequestManager(this);

            manager.setSendLoginHeaders(false);
            manager.setData(getData(email, password));
            manager.setUrl("https://discordapp.com/api/auth/login");
            manager.setType(RequestType.POST);

            final String response = manager.makeRequest();

            if (response == null)
                throw new LoginException("The provided email / password is wrong");

            System.out.println("Login successful!");

            authToken = new JSONObject(response).getString("token");

            getConfig().put("token", authToken);

            if (authToken == null || authToken.isEmpty())
                throw new LoginException("The token is null.");

            System.out.println(response);
            System.out.println("Created new token: " + authToken);

            RequestManager getGateway = new RequestManager(this);
            getGateway.setType(RequestType.GET);
            getGateway.setUrl("https://discordapp.com/api/gateway");

            gateway = new JSONObject(getGateway.makeRequest()).getString("url");
        }

        client = new WebSocketClientImpl(gateway, this);
    }

    public static void main(String[] args) throws LoginException {
        email = getConfig().getString("email");
        password = getConfig().getString("password");

        new Wrapper();
    }

    private static JSONObject getConfig() {
        final File config = new File("config.json");

        if (!config.exists()) {
            try {
                Files.write(Paths.get(config.getPath()),
                  new JSONObject()
                    .put("email", "")
                    .put("password", "")
                    .put("token", "")
                    .toString(4).getBytes()
                );

                System.out.println("The config.json was created. Populate with login information.");
                System.exit(0);
            } catch (JSONException | IOException exception) {
                exception.printStackTrace();
            }
        }
        try {
            return new JSONObject(Files.readString(Paths.get(config.getPath())));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private static String getData(String email, String password) {
        return new JSONObject()
          .put("email", email)
          .put("password", password)
          .toString();
    }

}
