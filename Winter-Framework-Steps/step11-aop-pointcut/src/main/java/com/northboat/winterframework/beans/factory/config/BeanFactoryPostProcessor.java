package com.northboat.winterframework.beans.factory.config;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {

    // 在 BeanFactory 设置属性之后，实例化 Bean 之前执行，修改 BeanDefinition 属性的机制
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
