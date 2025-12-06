package com.northboat.winterframework.test.bean;


import com.northboat.winterframework.beans.BeansException;
import com.northboat.winterframework.beans.factory.*;
import com.northboat.winterframework.context.ApplicationContext;

public class UserService {

    // 上下文和工厂感知
//    private ApplicationContext applicationContext;
//    private BeanFactory beanFactory;

    private String uId;
    private String company;
    private String location;
    private MyUserDao userDao;

    public String queryUserInfo(){
        return userDao.queryUserName(uId) + "," + company + "," + location;
    }
}