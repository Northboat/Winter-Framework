package com.northboat.winterframework.test.event;


import com.northboat.winterframework.context.ApplicationListener;
import com.northboat.winterframework.context.event.ContextClosedEvent;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("关闭事件：" + this.getClass().getName());
    }

}
