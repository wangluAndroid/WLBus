package com.serenity.wlbus.bus;

import java.lang.reflect.Method;

/**
 * Created by serenitynanian on 2018/6/14.
 * 执行表中集合中的元素对象
 * 属性：主要为Method对象，以及拥有此函数的对象
 */

public class ExecuteObj {

    private Object object;
    private Method executeMethod ;

    public ExecuteObj(Object object, Method executeMethod) {
        this.object = object;
        this.executeMethod = executeMethod;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(Method executeMethod) {
        this.executeMethod = executeMethod;
    }
}
