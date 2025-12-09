package com.northboat.winterframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.*;
import com.northboat.winterframework.beans.factory.config.AutowireCapableBeanFactory;
import com.northboat.winterframework.beans.factory.config.BeanDefinition;
import com.northboat.winterframework.beans.factory.config.BeanPostProcessor;
import com.northboat.winterframework.beans.factory.config.BeanReference;
import com.northboat.winterframework.beans.PropertyValue;
import com.northboat.winterframework.beans.PropertyValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

// 实现 createBean 函数，完成 Bean 的自动注入
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

//    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    // 一个 BeanDefinition 包括这个 Bean 对应的 Class 类和其内部属性列表
    // 若内部属性也为 Bean，那么将在属性列表中标注为 BeanReference，BeanReference 中将存放这个 Bean 的名称，作为 getBean 的入参以获取实例对象
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            // 创建实例
            bean = createBeanInstance(beanDefinition, beanName, args);
            // 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
            // 执行初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 注册实现了 DisposableBean 接口或者 destroyMethodName 字段不为空的 Bean 对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 只有单例对象才放进内存中
        if(beanDefinition.isSingleton()){
            addSingleton(beanName, bean);
        }

        return bean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 若不是单例 Bean，不执行销毁方法
        if(!beanDefinition.isSingleton()){
            return;
        }
        // 若为单例对象，注册销毁方法进生命周期，在 BeanPostProcessor 的后置处理中执行（初始化方法在前置处理中执行）
        if(bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor<?> constructor = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        // 获取 Class 类的构造函数们
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        // 遍历构造函数，找到入参长度相同的构造函数
        for(Constructor<?> ctor: declaredConstructors){
            if(args != null && ctor.getParameterTypes().length == args.length){
                constructor = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructor, args);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    // Bean 属性填充
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try{
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for(PropertyValue propertyValue: propertyValues.getPropertyValues()){
                String name = propertyValue.name();
                Object value = propertyValue.value();

                // 就是说，如果当前的属性是 beanReference 的一个引用，说明这个属性也是一个 Bean
                // 那么就直接从 Map 中 getBean 以获取这个 Bean 的实例化对象
                if(value instanceof BeanReference beanReference){
                    // 如果 A 依赖于 B，获取 B 的实例化
                    value = getBean(beanReference.beanName());
                }
                BeanUtil.setFieldValue(bean, name, value);
            }
        }catch (Exception e){
            throw new BeansException("Error setting property values: " + beanName);
        }
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition){

        // 先进行感知系的属性封装
        if(bean instanceof Aware){
            if(bean instanceof BeanFactoryAware){
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if(bean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if(bean instanceof BeanNameAware){
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        // BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 执行对象的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean [" +  beanName + "] failed", e);
        }

        // After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }


    // 执行 afterPropertiesSet 和初始化方法
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception{
        // 若当前 bean 实现了 initializingBean 接口，执行这里的 afterPropertiesSet 方法，但实际上初始化方法并不是在这执行，而是在下面的反射
        if(bean instanceof InitializingBean){
            ((InitializingBean)bean).afterPropertiesSet();
        }
        // 通过反射 invoke 执行初始化方法
        String initMethodName = beanDefinition.getInitMethodName();
        if(StrUtil.isNotEmpty(initMethodName)){
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            initMethod.invoke(bean);
        }
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor processor: getBeanPostProcessors()){
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if(current == null){
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor processor: getBeanPostProcessors()){
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if(current == null){
                return result;
            }
            result = current;
        }
        return result;
    }

}
