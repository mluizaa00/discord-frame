package com.luizaprestes.wrapper.event.client;

import com.luizaprestes.wrapper.WrapperClient;
import com.luizaprestes.wrapper.event.EventAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class EventLoader {

    private final WrapperClient client;

    public EventLoader(WrapperClient client) {
        this.client = client;
    }

    public void loadEvents(Object... holders) {
        for (Object holder : holders) {
            load(client, holder);
        }
    }

    public void removeEvents(Object... holders) {
        for (Object holder : holders) {
            client.getEventClient().remove(holder);
        }
    }

    public void load(final WrapperClient client, final Object holder) {
        for (Method method : holder.getClass().getMethods()) {
            final EventAdapter annotation = method.getAnnotation(EventAdapter.class);
            if (annotation == null) break;

            if (!Modifier.isPublic(method.getModifiers())) continue;

            try {
                method.invoke(holder);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                exception.printStackTrace();
            }

            client.getEventClient().register(holder);
        }
    }

}
