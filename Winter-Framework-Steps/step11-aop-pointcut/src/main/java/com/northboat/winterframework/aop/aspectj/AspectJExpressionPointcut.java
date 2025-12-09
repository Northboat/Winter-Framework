package com.northboat.winterframework.aop.aspectj;

import com.northboat.winterframework.aop.ClassFilter;
import com.northboat.winterframework.aop.MethodMatcher;
import com.northboat.winterframework.aop.Pointcut;

import java.lang.reflect.Method;

public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {
    @Override
    public boolean matches(Class<?> clazz) {
        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> clazz) {
        return false;
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
