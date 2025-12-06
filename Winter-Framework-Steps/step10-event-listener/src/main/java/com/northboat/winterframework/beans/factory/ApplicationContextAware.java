package com.northboat.winterframework.beans.factory;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.context.ApplicationContext;

public interface ApplicationContextAware extends Aware{
    void setApplicationContext(ApplicationContext context) throws BeansException;
}
