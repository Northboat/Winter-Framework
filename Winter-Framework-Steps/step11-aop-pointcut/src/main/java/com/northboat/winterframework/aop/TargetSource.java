package com.northboat.winterframework.aop;

public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    // 返回当前对象的类继承的所有接口类，用以确定当前对象到底继承了哪些类
    public Class<?>[] getTargetClass(){
        return this.target.getClass().getInterfaces();
    }

    public Object getTarget(){
        return this.target;
    }

}
