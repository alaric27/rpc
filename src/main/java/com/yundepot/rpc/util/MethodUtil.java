package com.yundepot.rpc.util;

import java.lang.reflect.Method;

/**
 * @author zhaiyanan
 * @date 2022/6/19  20:52
 */
public class MethodUtil {

    public static Method getMethod(Class clazz, String methodName) {
        Method[] methods = clazz.getDeclaredMethods();
        Method method = null;
        for (Method md : methods) {
            if (md.getName().equals(methodName)) {
                method = md;
                break;
            }
        }
        return method;
    }
}
