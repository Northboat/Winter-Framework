package com.northboat.winterframework.beans.factory;

import com.northboat.winterframework.beans.BeansException;

public interface BeanFactoryAware extends Aware{

    // 感知 BeanFactory 的接口
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
