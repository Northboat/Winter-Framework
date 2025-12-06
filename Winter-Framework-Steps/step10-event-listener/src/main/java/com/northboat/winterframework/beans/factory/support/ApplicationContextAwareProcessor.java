package com.northboat.winterframework.beans.factory.support;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.ApplicationContextAware;
import com.northboat.winterframework.beans.factory.config.BeanPostProcessor;
import com.northboat.winterframework.context.ApplicationContext;

public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ApplicationContextAware){
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
