package com.codegym.servletTask.model;

import java.lang.reflect.Type;

public class Question extends AbstractEntity {
    private Type type;

    public Question (Integer id, String content, Type type) {
        super(id, content);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    enum Type {
        USUAL,
        WON,
        LOST
    }
}
