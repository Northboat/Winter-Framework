package com.northboat.winterframework.beans.factory.support;


import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

// 简单的 JDK 实例化（通过反射机制）
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args) throws BeansException {
        Class<?> clazz = beanDefinition.getBeanClass();
        try {
            if(ctor != null) {
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new BeansException("Instantiate bean [" + clazz.getName() + "]", e);
        }
    }
}
