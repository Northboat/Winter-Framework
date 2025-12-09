package com.northboat.winterframework.beans.factory.config;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.BeanFactory;
import com.northboat.winterframework.beans.factory.HierarchicalBeanFactory;

// 引入 BeanPostProcessors 的 postProcessBeforeInitialization 和 postProcessorsAfterInitialization 方法，完善 Bean 的创建
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
