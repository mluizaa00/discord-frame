package com.luizaprestes.frame.event.client;

import com.luizaprestes.frame.event.Event;
import com.luizaprestes.frame.event.EventAdapter;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

@AllArgsConstructor
public class EventParser {

    private final EventClient client;

    public void parse(Event event) {
        for (Object holder : client.listenerList.toArray()) {
            for (Method method : holder.getClass().getMethods()) {
                final EventAdapter annotation = method.getAnnotation(EventAdapter.class);
                if (annotation == null || !Modifier.isPublic(method.getModifiers())) break;

                final Class<?> annotationClass = annotation.event().getEventClass();
                if (!event.getClass().getSimpleName().equals(annotationClass.getSimpleName())) break;

                for (Parameter parameter : method.getParameters()) {
                    if (annotationClass.equals(parameter.getType())) {
                        try {
                            method.invoke(holder);
                        } catch (IllegalAccessException | InvocationTargetException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
