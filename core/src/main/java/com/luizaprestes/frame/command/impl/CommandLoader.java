package com.luizaprestes.frame.command.impl;


import com.luizaprestes.frame.command.annotation.Command;
import com.luizaprestes.frame.command.model.CommandModel;
import com.luizaprestes.frame.entities.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class CommandLoader {

    private static final Class<?>[] CLASSES = new Class[]{
      Message.class,
      String[].class
    };

    public void load(final CommandFrameImpl frame, final Object holder) {
        for (Method method : holder.getClass().getMethods()) {
            final Command annotation = method.getAnnotation(Command.class);
            if (annotation == null) continue;

            if (!Arrays.equals(method.getParameterTypes(), CLASSES)
              || !Modifier.isPublic(method.getModifiers())
            ) continue;

            final CommandModel model = new CommandModel(
              annotation.name(),
              annotation.aliases(),
              annotation.permissions(),
              annotation.role()
            ) {
                @Override
                public void onCommand(Message context, String[] args) {
                    try {
                        method.invoke(holder, context, args);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            };

            frame.registerCommands(model);
        }
    }
}
