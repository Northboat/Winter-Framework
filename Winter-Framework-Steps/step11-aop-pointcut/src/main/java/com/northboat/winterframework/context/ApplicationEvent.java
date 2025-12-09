package com.northboat.winterframework.context;

import java.util.EventObject;

// EventObject 是 java.util 包中的类，继承了序列化接口
public abstract class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source) {
        super(source);
    }
}
