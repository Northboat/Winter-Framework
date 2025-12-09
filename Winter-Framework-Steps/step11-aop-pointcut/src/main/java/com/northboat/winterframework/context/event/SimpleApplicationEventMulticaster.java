package com.northboat.winterframework.context.event;

import com.northboat.winterframework.beans.factory.BeanFactory;
import com.northboat.winterframework.context.ApplicationEvent;
import com.northboat.winterframework.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    // 就是对监听器的一个遍历，符合对应事件的执行 onApplicationEvent 方法
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for(final ApplicationListener listener : getApplicationListeners(event)){
            listener.onApplicationEvent(event);
        }
    }
}
