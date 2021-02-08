package com.luizaprestes.frame.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luizaprestes.frame.Frame;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
public class Configuration extends JSONObject {

    private final Frame frame;
    private final File file;

    public Configuration(Frame frame, String name) {
        this.frame = frame;
        this.file = new File(name + ".json");

        create(name);
    }

    public String getString(final String path) {
        return getFile().getString(path);
    }

    public int getInteger(final String path) {
        return getFile().getInt(path);
    }

    public boolean getBoolean(final String path) {
        return getFile().getBoolean(path);
    }

    public double getDouble(final String path) {
        return getFile().getDouble(path);
    }

    public float getFloat(final String path) {
        return getFile().getFloat(path);
    }

    public long getLong(final String path) {
        return getFile().getLong(path);
    }

    public JSONObject get(final String path) {
        return getFile().getJSONObject(path);
    }

    public <T> T fromJson(final String json, final Class<T> clazz) {
        try {
            return frame.getJacksonAdapter().getFactory().readValue(json, clazz);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public <T> String toJson(final T value) {
        try {
            return frame.getJacksonAdapter().getFactory().writeValueAsString(value);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public JSONObject getFile() {
        try {
            return new JSONObject(Files.readAllLines(Paths.get(file.getPath())));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private void create(final String name) {
        if (!file.exists()) {
            try {
                frame.getLogger().atInfo().log("The configuration " + name + ".json was created.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

}
