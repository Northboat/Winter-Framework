package com.northboat.winterframework.context;

import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext context) throws BeansException;
}
