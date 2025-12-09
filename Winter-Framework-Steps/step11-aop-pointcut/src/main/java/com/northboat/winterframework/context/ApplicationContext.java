package com.northboat.winterframework.context;

import com.northboat.winterframework.beans.factory.ListableBeanFactory;

// 扩展 ListableBeanFactory，继承了 getBean, getBeanOfType, getBeanNames 等方法
public interface ApplicationContext extends ListableBeanFactory, ApplicationEventPublisher {
}
