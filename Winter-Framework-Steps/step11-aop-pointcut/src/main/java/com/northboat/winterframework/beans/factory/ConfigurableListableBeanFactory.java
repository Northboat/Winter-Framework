package com.northboat.winterframework.beans.factory;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.config.AutowireCapableBeanFactory;
import com.northboat.winterframework.beans.factory.config.BeanDefinition;
import com.northboat.winterframework.beans.factory.config.ConfigurableBeanFactory;


public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;
}
