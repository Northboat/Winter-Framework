package com.northboat.winterframework.context.support;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.ConfigurableListableBeanFactory;
import com.northboat.winterframework.beans.factory.config.BeanFactoryPostProcessor;
import com.northboat.winterframework.beans.factory.config.BeanPostProcessor;
import com.northboat.winterframework.context.ApplicationEvent;
import com.northboat.winterframework.context.ApplicationListener;
import com.northboat.winterframework.context.ConfigurableApplicationContext;
import com.northboat.winterframework.context.event.ApplicationEventMulticaster;
import com.northboat.winterframework.context.event.ContextRefreshedEvent;
import com.northboat.winterframework.context.event.SimpleApplicationEventMulticaster;
import com.northboat.winterframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

// 引入资源加载器
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    // 维护一个内置的广播器 Bean
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";
    private ApplicationEventMulticaster applicationEventMulticaster;

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

        // 初始化事件发布者
        initApplicationEventMulticaster();

        // 注册事件监听器
        registerListeners();

        // 实例化
        beanFactory.preInstantiateSingletons();

        // 发布容器刷新完成事件
        finishRefresh();
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners(){
        // 从 Bean Factory 中获取所有 ApplicationListener 子类对象
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for(ApplicationListener<?> listener: applicationListeners){
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh(){
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
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
