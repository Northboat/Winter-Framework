package com.northboat.winterframework.context.event;

import com.northboat.winterframework.context.ApplicationEvent;
import com.northboat.winterframework.context.ApplicationListener;

public interface ApplicationEventMulticaster {
    // 添加监听器
    void addApplicationListener(ApplicationListener<?> listener);

    // 移除监听器
    void removeApplicationListener(ApplicationListener<?> listener);

    // 向所有监听器广播事件
    void multicastEvent(ApplicationEvent event);
}
