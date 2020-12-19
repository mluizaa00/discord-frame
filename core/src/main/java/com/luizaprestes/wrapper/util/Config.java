package com.luizaprestes.wrapper.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
public class Config {

    public static JSONObject getConfig() {
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
}
