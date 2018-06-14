package com.serenity.wlbus.bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by serenitynanian on 2018/6/14.
 */

public class WLBus {
    private static volatile WLBus instances ;


    /**
     * 缓存表
     * key:Class  value:为被Subscribe注解的函数对象
     */
    private Map<Class, List<SubscribeMehtodObject>> METHOD_CACHE = new HashMap<>();
    private Map<String, List<ExecuteObj>> EXECUTE = new HashMap<>();
    private Map<Class, List<String>> UNREGISTER = new HashMap<>();


    private WLBus(){}

    public static WLBus getDefault(){
        if (instances == null) {
            synchronized (WLBus.class) {
                if (instances == null) {
                    instances = new WLBus();
                }
            }
        }
        return instances;
    }


    /**
     * 注册
     */
    public void register(Object object){
        Class<?> aClass = object.getClass();
        //添加或查找缓存表   一个label对象一个SubscribeMehtodObject对象
        List<SubscribeMehtodObject> subscribeMehtodObjects = registerNewObj(aClass);


        //反注册表Class ----- [标签1，标签2]
        List<String> strings = UNREGISTER.get(aClass);
        if (null == strings) {
            strings = new ArrayList<>();
            UNREGISTER.put(aClass, strings);
        }

        //制作执行函数的表
        for (SubscribeMehtodObject subscribeMehtodObject : subscribeMehtodObjects) {
            String label = subscribeMehtodObject.getLabel();
            List<ExecuteObj> executeObjs = EXECUTE.get(label);
            if (null == executeObjs) {
                executeObjs = new ArrayList<>();
                EXECUTE.put(label, executeObjs);
            }
            ExecuteObj executeObj = new ExecuteObj(object, subscribeMehtodObject.getSubscribeMehod());
            executeObjs.add(executeObj);

            //反注册表
            if (!strings.contains(label)) {
                strings.add(label);
            }

        }
    }


    /**
     * 注销
     * @param object
     */
    public void unRegister(Object object) {
        List<String> strings = UNREGISTER.get(object.getClass());
        if (null != strings) {
            for (String string : strings) {
                List<ExecuteObj> executeObjs = EXECUTE.get(string);
                if (null != executeObjs) {
                    Iterator<ExecuteObj> iterator = executeObjs.iterator();
                    //这个必须使用迭代器---因为：要删除集合中的元素，如果使用for循环中，是不能删除元素的；
                    while (iterator.hasNext()) {
                        ExecuteObj next = iterator.next();
                        //对象是同一个才删除
                        if (next.getObject() == object) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }


    public void release(){
        METHOD_CACHE.clear();
        EXECUTE.clear();
        UNREGISTER.clear();

        METHOD_CACHE = null ;
        EXECUTE = null ;
        UNREGISTER = null ;


    }

    private List<SubscribeMehtodObject> registerNewObj(Class<?> aClass) {
        List<SubscribeMehtodObject> subscribeMehtodObjects = METHOD_CACHE.get(aClass);
        //先看下缓存中是否有这个class
        if (null != subscribeMehtodObjects) {
            return subscribeMehtodObjects;
        }else{
            subscribeMehtodObjects = new ArrayList<>();
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                //获取此方法中是否有Subscribe注解
                Subscribe annotation = declaredMethod.getAnnotation(Subscribe.class);
                if (annotation != null) {
                    String[] values = annotation.value();
                    Class<?>[] parameterTypes = declaredMethod.getParameterTypes();//获取方法中的参数类型
                    for (String value : values) {
                        declaredMethod.setAccessible(true);
                        SubscribeMehtodObject subscribeMehtodObject = new SubscribeMehtodObject(declaredMethod, value, parameterTypes);
                        subscribeMehtodObjects.add(subscribeMehtodObject);
                    }
                }
            }
            METHOD_CACHE.put(aClass, subscribeMehtodObjects);
        }
        return subscribeMehtodObjects;
    }




    public void post(String lable,Object... objects){

        List<ExecuteObj> executeObjs = EXECUTE.get(lable);
        if (null == executeObjs) {
            return;
        }else{
            for (ExecuteObj executeObj : executeObjs) {
                Method executeMethod = executeObj.getExecuteMethod();
                //执行函数需要的参数类型数组
                Class<?>[] parameterTypes = executeMethod.getParameterTypes();
                //如果传递过的参数小于实际需要的
                Object[] realParams = new Object[parameterTypes.length];
                if (objects != null) {
                    for (int i = 0; i < parameterTypes.length; i++) {
                        if (i < objects.length && parameterTypes[i].isInstance(objects[i])) {
                            realParams[i] = objects[i];
                        }else{
                            realParams[i] = null ;
                        }
                    }
                }
                Object object = executeObj.getObject();
                try {
                    executeMethod.invoke(object, realParams);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
