package com.yundepot.rpc;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 设计思路参考HTTP请求
 * 为了跨语言，必须做出一些取舍。放弃了参数类型字段，同时限制同一个service下method必须唯一(对于重载的情况，可以考虑使用注解的方式自定义methodName)。
 * serviceName: 服务提供者的唯一标识。在java语言下用于定位调用哪个类
 * methodName: 方法名，类比http中的url，同一个service中必须唯一
 * parameters: 方法的参数
 * Class<?>[] parameterTypes; 由于限制了同一个service 下method唯一，所以不存在重载的情况，也就不需要该参数
 *
 * @author zhaiyanan
 * @date 2019/6/9 15:52
 */
public class RpcRequest implements Serializable {

    /**
     * 服务提供者的唯一标识。在java语言下用于定位调用哪个类
     */
    private String serviceName;

    /**
     * 请求的方法名
     */
    private String methodName;

    /**
     * 方法参数
     */
    private Object[] methodArgs;

    public RpcRequest() {

    }

    public RpcRequest(Method method, Object[] args) {
        this.serviceName = method.getDeclaringClass().getName();
        this.methodName = method.getName();
        this.methodArgs = args;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getMethodArgs() {
        return methodArgs;
    }

    public void setMethodArgs(Object[] methodArgs) {
        this.methodArgs = methodArgs;
    }
}
