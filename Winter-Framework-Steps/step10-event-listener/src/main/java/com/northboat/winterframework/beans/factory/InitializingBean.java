package com.northboat.winterframework.beans.factory;

public interface InitializingBean {
    // 在属性填充后，通过反射 invoke 的方式执行初始化方法
    void afterPropertiesSet() throws Exception;
}
