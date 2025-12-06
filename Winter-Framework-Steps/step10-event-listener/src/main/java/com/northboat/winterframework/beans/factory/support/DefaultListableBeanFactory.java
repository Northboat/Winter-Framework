package com.northboat.winterframework.beans.factory.support;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.ConfigurableListableBeanFactory;
import com.northboat.winterframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

// 默认的 Bean 工厂，实现 Bean 的注册和获取 BeanDefinition 的具体逻辑
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap;

    public DefaultListableBeanFactory() {
        this.beanDefinitionMap = new HashMap<>();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition){
        beanDefinitionMap.put(beanName, beanDefinition);
    }


    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
           Class<?> beanClass = beanDefinition.getBeanClass();
           if(type.isAssignableFrom(beanClass)){
               result.put(beanName, (T) getBean(beanName));
           }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null){
            throw new BeansException("No bean named '" + beanName + "' is defined" );
        }
        return beanDefinition;
    }

    // 实例化所有记录在案的 Bean
    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }
}
