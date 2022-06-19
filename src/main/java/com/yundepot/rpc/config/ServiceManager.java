package com.yundepot.rpc.config;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhaiyanan
 * @date 2022/6/19  20:12
 */
public class ServiceManager {
    private static Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    public static void addService(String serviceName, Object serviceBean) {
        if (!serviceMap.containsKey(serviceName)) {
            serviceMap.put(serviceName, serviceBean);
        }
    }

    public static Object getService(String service) {
        return serviceMap.get(service);
    }
}
