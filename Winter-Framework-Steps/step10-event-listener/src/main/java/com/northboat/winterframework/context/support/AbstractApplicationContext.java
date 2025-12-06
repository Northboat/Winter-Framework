package com.northboat.winterframework.context.support;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.ConfigurableListableBeanFactory;
import com.northboat.winterframework.beans.factory.config.BeanDefinition;
import com.northboat.winterframework.beans.factory.config.BeanFactoryPostProcessor;
import com.northboat.winterframework.beans.factory.config.BeanPostProcessor;
import com.northboat.winterframework.beans.factory.config.ConfigurableBeanFactory;
import com.northboat.winterframework.beans.factory.support.ApplicationContextAwareProcessor;
import com.northboat.winterframework.context.ConfigurableApplicationContext;
import com.northboat.winterframework.core.io.DefaultResourceLoader;

import java.util.Map;

// 引入资源加载器
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 创建 beanFactory 并加载 BeanDefinition
        refreshBeanFactory();
        // 获取 beanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        // 载入上下文感知器
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        // 在 Bean 实例化前执行 BeanFactoryPostProcessor 的修改 BeanDefinition 操作
        invokeBeanFactoryPostProcessors(beanFactory);
        // 注册 BeanPostProcessor
        registerBeanPostProcessors(beanFactory);
        // 实例化
        beanFactory.preInstantiateSingletons();
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        // 获取所有 BeanPostProcessor
        // 遍历 BeanPostProcessor
        // 调用 postProcessBeanFactory
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()){
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        // 获取所有 BeanPostProcessor
        // 遍历 BeanPostProcessor
        // 注册 BeanPostProcessor
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for(BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()){
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }


    @Override
    public void close(){
        getBeanFactory().destroySingletons();
    }


    // 这里的线程钩子注册，实际上是当前对象销毁时执行的方法，即在 Context 销毁时执行 close 方法
    @Override
    public void registerShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(this::close) {});
    }





    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

}
