package com.northboat.winterframework.context;

import com.northboat.winterframework.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext{

    // 刷新容器
    void refresh() throws BeansException;

    // 销毁 Bean 工厂中所有单例
    void close();

    // 向虚拟机添加钩子，以观测销毁方法的调用
    void registerShutdownHook();
}
