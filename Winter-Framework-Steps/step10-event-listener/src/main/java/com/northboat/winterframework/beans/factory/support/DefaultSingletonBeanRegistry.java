package com.northboat.winterframework.beans.factory.support;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.DisposableBean;
import com.northboat.winterframework.beans.factory.config.SingletonBeanRegistry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    // 设置一个空值，作为 Map 的占位
    protected static final Object NULL_OBJECT = new Object();

    // 因为这个涉及读写，我感觉还是得用 ConcurrentHashMap
    private Map<String, Object> singletonObjects;
    private final Map<String, DisposableBean> disposableBeans;

    public DefaultSingletonBeanRegistry() {
        this.singletonObjects = new ConcurrentHashMap<>();
        this.disposableBeans = new LinkedHashMap<>();
    }


    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    // 销毁单例
    @Override
    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

    public void registerDisposableBean(String beanName, DisposableBean disposableBean) {
        disposableBeans.put(beanName, disposableBean);
    }

    protected void addSingleton(String beanName, Object bean){
        singletonObjects.put(beanName, bean);
    }
}
