package com.northboat.winterframework.beans;

import java.util.ArrayList;
import java.util.List;

public record PropertyValues(List<PropertyValue> propertyValueList) {

    public PropertyValues(){
        this(new ArrayList<>());
    }

    public void addPropertyValue(PropertyValue propertyValue){
        this.propertyValueList.add(propertyValue);
    }

    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName){
        for(PropertyValue propertyValue: propertyValueList){
            if(propertyValue.name().equals(propertyName)){
                return propertyValue;
            }
        }
        return null;
    }
}
