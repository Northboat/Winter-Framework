package com.northboat.winterframework.beans.factory;

public interface DisposableBean {
    void destroy() throws Exception;
}
