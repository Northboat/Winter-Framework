package com.northboat.winterframework.beans.factory.support;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.BeanFactory;
import com.northboat.winterframework.beans.factory.FactoryBean;
import com.northboat.winterframework.beans.factory.config.BeanDefinition;
import com.northboat.winterframework.beans.factory.config.BeanPostProcessor;
import com.northboat.winterframework.beans.factory.config.ConfigurableBeanFactory;
import com.northboat.winterframework.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

// 继承 DefaultSingletonBeanRegistry，继承了单例 Bean 的注册和获取逻辑
// 实现 BeanFactory getBean 接口逻辑，具体的 getBeanDefinition 和 createBean 都是抽象方法，由子类实现
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private final ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    // 实现 BeanFactory 的 getBean 接口
    @Override
    public Object getBean(String beanName){
        return doGetBean(beanName, null);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName, args);
    }

    // 返回相应对象，在这做一个强转的封装
    @Override
    public <T> T getBean(String beanName, Class<T> type) throws BeansException {
        return (T) getBean(beanName);
    }


    protected <T> T doGetBean(final String beanName, final Object[] args) throws BeansException {
        Object sharedInstance = getSingleton(beanName);
        if(sharedInstance != null){
            return (T) getObjectForBeanInstance(sharedInstance, beanName);
        }

        // 当 Bean Object 并未初始化，使用 Class 对象进行实例化
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, beanName);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName){
        if(!(beanInstance instanceof FactoryBean<?> factoryBean)){
            return beanInstance;
        }

        // factoryBean 在 if 语句中定义
        // 先从缓存中获取
        Object object = getCachedObjectForFactoryBean(beanName);
        // 若缓存中不存在，则从 factoryBean 中获取
        if(object == null){
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors(){
        return beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader(){
        return beanClassLoader;
    }


    // 获取 Bean Class 实例，用于创建 Bean Object
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    // 根据入参 args 创建 Object 并存入单例的 Map 中
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException;


}
