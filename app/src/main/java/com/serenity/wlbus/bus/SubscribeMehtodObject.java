package com.serenity.wlbus.bus;

import java.lang.reflect.Method;

/**
 * Created by serenitynanian on 2018/6/14.
 * 缓存表中的函数封装类：此类里面包含函数的Method对象，函数需要的参数类型，以及注解上面的label
 */

public class SubscribeMehtodObject {

    private Method subscribeMehod ;
    private String label ;
    private Class[] parameterClass ;//参数类型

    public SubscribeMehtodObject(Method subscribeMehod, String label, Class[] parameterClass) {
        this.subscribeMehod = subscribeMehod;
        this.label = label;
        this.parameterClass = parameterClass;
    }

    public Method getSubscribeMehod() {

        return subscribeMehod;
    }

    public void setSubscribeMehod(Method subscribeMehod) {
        this.subscribeMehod = subscribeMehod;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Class[] getParameterClass() {
        return parameterClass;
    }

    public void setParameterClass(Class[] parameterClass) {
        this.parameterClass = parameterClass;
    }
}
