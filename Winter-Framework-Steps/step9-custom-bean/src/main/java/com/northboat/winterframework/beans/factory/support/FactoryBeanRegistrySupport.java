package com.northboat.winterframework.beans.factory.support;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{

    // Object 的缓存，先从这取，如果没有直接从 FactoryBean 中获取，再存入缓存
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String beanName){
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName){
        // 若为单例
        if(factory.isSingleton()){
            Object object = this.factoryBeanObjectCache.get(beanName);
            if(object == null){
                object = doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
            }
            return (object != NULL_OBJECT ? object : null);
        } else {
            // 若不是单例，直接通过 BeanFactory 返回
            return doGetObjectFromFactoryBean(factory, beanName);
        }

    }

    private Object doGetObjectFromFactoryBean(FactoryBean factory, String beanName){
        try {
            return factory.getObject();
        } catch (Exception e){
            throw new BeansException("FactoryBean threw exception on object [" + beanName + "] creation", e);
        }
    }
}
