package com.northboat.winterframework.context.event;

import com.northboat.winterframework.context.ApplicationContext;
import com.northboat.winterframework.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }
}
