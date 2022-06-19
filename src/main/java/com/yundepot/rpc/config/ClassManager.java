package com.yundepot.rpc.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 辅助client端，解析返回类型
 * @author zhaiyanan
 * @date 2022/6/19  21:47
 */
public class ClassManager {
    private static Map<String, Class> classMap = new ConcurrentHashMap<>();

    public static void addCLass(String serviceName, Class clazz) {
        if (!classMap.containsKey(serviceName)) {
            classMap.put(serviceName, clazz);
        }
    }

    public static Class getClass(String serviceName) {
        return classMap.get(serviceName);
    }
}
