package com.northboat.winterframework.context.support;

import com.northboat.winterframework.beans.factory.support.DefaultListableBeanFactory;
import com.northboat.winterframework.beans.factory.xml.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        // AbstractApplicationContext 继承了 DefaultResourceLoader
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] locations = getConfigLocations();
        // 将配置载入 beanFactory
        if(locations != null){
            beanDefinitionReader.loadBeanDefinitions(locations);
        }
    }

    protected abstract String[] getConfigLocations();
}
