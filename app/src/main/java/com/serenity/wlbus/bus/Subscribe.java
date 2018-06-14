package com.serenity.wlbus.bus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by serenitynanian on 2018/6/14.
 * 自定义注解，用来表示需要接受通知的方法
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    /**
     * 注解的属性也叫做：成员变量。
     * 注解只有成员变量，没有方法。
     * 注解的成员变量在注解的定义中'以无形参的方法'形式来声明，其方法名定义了该成员变量的名字，其返回值定义了该成员变量的类型；
     * <p>
     * 备注：
     * 1.如果一个注解内仅仅只有一个名字为value的属性时，应用这个注解时，可以直接将属性值填写到括号内；//@Subscribe("wanglu")
     * 2.如果一个注解没有任何属性，在应用这个注解的时候，括号可以省略；
     * 3.如果一个注解的属性是数组，在应用的时候使用{}括起来；////@Subscribe({"wanglu","andriod"})
     */

    //label，客户端可以给指定label发送通知消息
    String[] value();
}
