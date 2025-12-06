package com.northboat.winterframework.beans.factory;

import com.northboat.winterframework.beans.BeansException;

public interface BeanClassLoaderAware extends Aware{

    void setBeanClassLoader(ClassLoader classLoader) throws BeansException;
}
