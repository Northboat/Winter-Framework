package com.northboat.winterframework.beans.factory.config;

import com.northboat.winterframework.beans.BeansException;

public interface BeanPostProcessor {

    // 在 Bean 对象执行初始化方法之前执行，修改 Bean 对象
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    // 在 Bean 对象执行初始化方法之后执行，修改 Bean 对象
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
