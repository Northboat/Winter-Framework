package com.northboat.winterframework.beans.factory;

// 提供自定义 Bean 接口，用户的自定义 Bean 将通过这个接口实现
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();
}
