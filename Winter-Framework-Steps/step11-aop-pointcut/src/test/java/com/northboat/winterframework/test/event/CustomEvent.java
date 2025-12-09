package com.northboat.winterframework.test.event;

import com.northboat.winterframework.context.ApplicationEvent;

public class CustomEvent extends ApplicationEvent {

    private Long id;
    private String message;

    public CustomEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id.toString();
    }

    public String getMessage() {
        return message;
    }
}
