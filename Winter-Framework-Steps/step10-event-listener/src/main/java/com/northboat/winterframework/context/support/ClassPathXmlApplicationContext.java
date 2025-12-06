package com.northboat.winterframework.context.support;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.xml.XmlBeanDefinitionReader;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    public ClassPathXmlApplicationContext(String configLocation) throws BeansException{
        this(new String[]{configLocation});
    }

    public ClassPathXmlApplicationContext(String[] configLocation) throws BeansException{
        this.configLocations = configLocation;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
