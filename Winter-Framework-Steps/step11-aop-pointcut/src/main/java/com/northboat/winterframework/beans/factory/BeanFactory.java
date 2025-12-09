package com.northboat.winterframework.beans.factory;


import com.northboat.winterframework.beans.BeansException;

public interface BeanFactory {

    public Object getBean(String beanName) throws BeansException;

    // 传递构造函数入参获取实例对象
    public Object getBean(String beanName, Object... args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
