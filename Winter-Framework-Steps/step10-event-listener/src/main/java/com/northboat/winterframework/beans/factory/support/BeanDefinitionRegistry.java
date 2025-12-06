package com.northboat.winterframework.beans.factory.support;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    // 注册 BeanDefinition
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    // 根据 Bean 名称查询 BeanDefinition
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    // 判断是否含有指定的 BeanDefinition
    boolean containsBeanDefinition(String beanName);

    // 返回注册表中所有 Bean 名称
    String[] getBeanDefinitionNames();
}
