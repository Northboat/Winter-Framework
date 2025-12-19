package com.northboat.winterframework.aop;

import org.aopalliance.intercept.MethodInterceptor;

public class AdvisedSupport {
    // 被代理的目标对象
    private TargetSource targetSource; // 内部维护一个 Object
    // 方法拦截器，来自于 aop alliance
    private MethodInterceptor methodInterceptor;
    // 方法匹配器(检查目标方法是否符合通知条件)
    private MethodMatcher methodMatcher;

    // ...get/set
    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }
}
