package com.northboat.winterframework.beans.factory.config;

import com.northboat.winterframework.beans.PropertyValues;

public class BeanDefinition {

    private final String SCOPE_SINGLETON = "singleton";
    private final String SCOPE_PROTOTYPE = "prototype";

    private String scope = SCOPE_SINGLETON;
    private boolean singleton = true;
    private boolean prototype = false;

    private Class<?> beanClass;
    private PropertyValues propertyValues;
    private String initMethodName;
    private String destroyMethodName;

    public BeanDefinition(Class<?> beanClass){
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues){
        this.beanClass = beanClass;
        // 若传入的 propertyValues 为空，则创建一个空的 propertyValues
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }
    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }
    public String getInitMethodName() {
        return initMethodName;
    }
    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
    public String getDestroyMethodName() {
        return destroyMethodName;
    }
    public PropertyValues getPropertyValues() {
        return propertyValues;
    }
    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
    public void setScope(String scope){
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }
    public boolean isSingleton() {
        return singleton;
    }
    public boolean isPrototype() {
        return prototype;
    }
    public String getScope() {
        return scope;
    }
}
