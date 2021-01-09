package com.luizaprestes.frame.command.annotation;

import com.luizaprestes.frame.entities.guild.model.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    String name();

    String[] aliases() default {};

    Permission[] permissions() default {};

    long role() default 0;
}
