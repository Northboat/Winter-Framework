package com.northboat.winterframework.beans.factory.support;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

// 通过 CGLib 字节码构造对象进行实例化
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args) throws BeansException {

        Enhancer enhancer = new Enhancer();
        // 根据原有类创建代理类
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(NoOp.INSTANCE);
        if(ctor == null){
            return enhancer.create();
        }
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
