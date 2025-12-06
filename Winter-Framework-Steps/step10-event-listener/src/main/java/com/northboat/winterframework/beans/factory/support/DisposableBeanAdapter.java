package com.northboat.winterframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.northboat.winterframework.beans.factory.DisposableBean;
import com.northboat.winterframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        // 若是 bean 实现了 DisposableBean 接口，直接调用其 destroy 方法
        if(bean instanceof DisposableBean){
            ((DisposableBean)bean).destroy();
        }

        // 若 bean 没有实现 DisposableBean 接口，则通过反射执行 destroy 方法
        if(StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean)){
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            destroyMethod.invoke(bean);
        }
    }
}
