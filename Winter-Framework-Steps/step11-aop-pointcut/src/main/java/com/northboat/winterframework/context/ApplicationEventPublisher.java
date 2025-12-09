package com.northboat.winterframework.context;

public interface ApplicationEventPublisher {

    // 发布事件接口
    void publishEvent(ApplicationEvent event);
}
