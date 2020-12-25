package com.luizaprestes.wrapper.util;

public class Logger {

    private final String name;
    private final boolean show;

    public <T> Logger(Class<T> clazz, boolean showClass) {
        this.name = clazz.getSimpleName();
        this.show = showClass;
    }

    public void info(String message) {
        if (show) {
            System.out.println("{" + name + "} [INFO] " + message);
        } else {
            System.out.println("[INFO] " + message);
        }
    }

    public void debug(String message) {
        if (show) {
            System.out.println("{" + name + "} [DEBUG] " + message);
        } else {
            System.out.println("[DEBUG] " + message);
        }
    }

    public void error(String message) {
        if (show) {
            System.out.println("{" + name + "} [ERROR] " + message);
        } else {
            System.out.println("[ERROR] " + message);
        }
    }

}
