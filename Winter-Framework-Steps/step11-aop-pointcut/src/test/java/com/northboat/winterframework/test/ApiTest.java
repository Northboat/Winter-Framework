package com.northboat.winterframework.test;

import com.northboat.winterframework.context.support.ClassPathXmlApplicationContext;
import com.northboat.winterframework.test.event.CustomEvent;
import org.junit.jupiter.api.Test;

public class ApiTest {

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:winter.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "成功了！"));

        applicationContext.registerShutdownHook();
    }

}
