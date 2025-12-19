package com.northboat.winterframework.test;

import com.northboat.winterframework.aop.AdvisedSupport;
import com.northboat.winterframework.aop.MethodMatcher;
import com.northboat.winterframework.aop.TargetSource;
import com.northboat.winterframework.aop.aspectj.AspectJExpressionPointcut;
import com.northboat.winterframework.aop.framework.Cglib2AopProxy;
import com.northboat.winterframework.aop.framework.JdkDynamicAopProxy;
import com.northboat.winterframework.aop.framework.ReflectiveMethodInvocation;
import com.northboat.winterframework.context.support.ClassPathXmlApplicationContext;
import com.northboat.winterframework.test.bean.IUserService;
import com.northboat.winterframework.test.bean.UserService;
import com.northboat.winterframework.test.bean.UserServiceInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ApiTest {

    @Test
    public void test_proxy_method() {
        // 目标对象(可以替换成任何的目标对象)
        Object targetObj = new UserService();
        // AOP 代理
        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), new InvocationHandler() {
            // 方法匹配器
            MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* com.northboat.winterframework.test.bean.IUserService.*(..))");
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (methodMatcher.matches(method, targetObj.getClass())) {
                    // 方法拦截器
                    MethodInterceptor methodInterceptor = invocation -> {
                        long start = System.currentTimeMillis();
                        try {
                            return invocation.proceed();
                        } finally {
                            System.out.println("监控 - Begin By AOP");
                            System.out.println("方法名称：" + invocation.getMethod().getName());
                            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
                            System.out.println("监控 - End\r\n");
                        }
                    };
                    // 反射调用
                    return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
                }
                return method.invoke(targetObj, args);
            }
        });
        String result = proxy.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.northboat.winterframework.test.bean.UserService.*(..))");
        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        // 类匹配
        System.out.println(pointcut.matches(clazz));
        // 类方法匹配
        System.out.println(pointcut.matches(method, clazz));

        // true、true
    }

    @Test
    public void test_dynamic() {
        // 目标对象
        IUserService userService = new UserService();

        // 组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.northboat.winterframework.test.bean.IUserService.*(..))"));

        // 代理对象(JdkDynamicAopProxy)
        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_jdk.queryUserInfo());

//        // 代理对象(Cglib2AopProxy)
//        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
//        // 测试调用
//        System.out.println("测试结果：" + proxy_cglib.register("花花"));
    }

}
